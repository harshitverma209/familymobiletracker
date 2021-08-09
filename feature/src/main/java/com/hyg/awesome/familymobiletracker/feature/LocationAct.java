package com.hyg.awesome.familymobiletracker.feature;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

public class LocationAct extends FragmentActivity {

    String server,userid,GroupId,GroupMemberId[],GroupMemberName[];
    Double Latitude[],Longitude[];
    private GoogleMap mMap;
    SupportMapFragment mapFragment;
    String Dated[];
    OnMapReadyCallback omrc;
    Boolean mapObtained=false;
    LatLng memberlocation[];
    LatLngBounds.Builder builder;
    LatLngBounds bounds;
    static ArrayList<Marker> markers;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_loc);
        server=getIntent().getStringExtra("server");

        userid=getIntent().getStringExtra("userid");
        GroupId=getIntent().getStringExtra("GroupId");
        GroupMemberId=getIntent().getStringArrayExtra("GroupMemberId");
        GroupMemberName=getIntent().getStringArrayExtra("GroupMemberName");
        memberlocation=new LatLng[GroupMemberId.length];


        markers=new ArrayList<>();


        getMemberLocation(Integer.parseInt(GroupMemberId[0]),0);


        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        omrc = new OnMapReadyCallback() {
            public void onMapReady(GoogleMap googleMap) {

                mapObtained=true;

                mMap = googleMap;

                // Add a marker in Sydney and move the camera


                markers.add(mMap.addMarker(new MarkerOptions().position(memberlocation[0]).title(GroupMemberName[0])));
                putMarkers();
                CameraUpdate zoom=CameraUpdateFactory.zoomTo(11.5f);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(memberlocation[0]));
                mMap.animateCamera(zoom);
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            }

        };
    }

    public void putMarkers() {
        for(int i=1;i<GroupMemberId.length;i++) {
            getMemberLocation(Integer.parseInt(GroupMemberId[i]),i);

        }

    }

    public void getMemberLocation(int memId, final int pos) {

        String url="http://"+server+"/LocationGetter.php?UserId="+userid+"&GroupId="+GroupId+"&GroupMemberId="+memId;
        StringRequest req=new StringRequest(Request.Method.POST,url,new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {
                try {
                    Log.d("chech","Response received:"+response.toString());
                    JSONObject obj=new JSONObject(response);
                    Log.d("chech","object found");
                    String qs=obj.getString("qs");
                    JSONArray Latitudes=obj.getJSONArray("Latitudes");
                    JSONArray Longitudes=obj.getJSONArray("Longitudes");
                    JSONArray Dateds=obj.getJSONArray("Dateds");
                    Latitude=new Double[Latitudes.length()];
                    Longitude=new Double[Longitudes.length()];
                    Dated=new String[Dateds.length()];
                    for(int i=0;i<Latitudes.length();i++){

                        Latitude[i]=Latitudes.getDouble(i);
                        Longitude[i]=Longitudes.getDouble(i);
                        Dated[i]=Dateds.getString(i);
                        memberlocation[pos] = new LatLng(Latitude[0], Longitude[0]);
                        //Log.d("chech",GroupMemberName[i]);

                    }
                    Log.d("chech",qs);

                    if(qs.equals("true")) {
                        if(!mapObtained){


                            mapFragment.getMapAsync(omrc);

                            Log.d("chech", "Obtaining map!");

                        }
                        else{

                            markers.add(mMap.addMarker(new MarkerOptions().position(memberlocation[pos]).title(GroupMemberName[pos])));
                            Log.d("chech",markers.size()+" markers");
                        }
                        if(pos>=(GroupMemberId.length-1)&&pos>0){
                            showMarkers();
                        }else{
                            if(mapObtained) {
                                CameraUpdate zoom = CameraUpdateFactory.zoomTo(10f);
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(memberlocation[pos]));
                                mMap.animateCamera(zoom);
                            }
                        }
                        //mMap.addMarker(new MarkerOptions().position(memberlocation).title("Marker on Location"));
                        //ListViewEnabler();
                        //Toast.makeText(ViewGroupActivity.this, "Group " + gname + " Added", Toast.LENGTH_LONG).show();
                        //finish();
                    }else{
                        //Toast.makeText(ViewGroupActivity.this,"Group ID taken",Toast.LENGTH_LONG).show();
                    }
                    //Toast.makeText(AddGroupActivity.this,"Welcome, "+name,Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    Log.d("chech","string not found");
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("chech",error.toString());
            }
        });
        RequestQueue rq= Volley.newRequestQueue(LocationAct.this);
        rq.add(req);
        req.setRetryPolicy(new DefaultRetryPolicy(5000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }

    public void showMarkers() {
        builder = new LatLngBounds.Builder();
        for (int n=0;n<markers.size();n++) {
            Log.d("chech", "Marker added");
            builder.include(markers.get(n).getPosition());
        }
        bounds = builder.build();
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * 0.10); // offset from edges of the map 10% of screen

        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(memberlocation[0]));
        mMap.animateCamera(cu);

    }
}
