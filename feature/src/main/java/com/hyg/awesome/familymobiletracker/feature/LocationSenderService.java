package com.hyg.awesome.familymobiletracker.feature;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.hyg.awesome.familymobiletracker.feature.permission.*;

import org.json.JSONException;
import org.json.JSONObject;

public class LocationSenderService extends Service {
    String name, userid,server;
    LocationManager lm;
    LocationListener ll;
    Double latitude,longitude;
    FusedLocationProviderClient client;
    LocationRequest lr;
    LocationCallback lc;
    /* public LocationSenderService() {

     }*/
    @SuppressLint("MissingPermission")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        name = intent.getStringExtra("name");
        userid = intent.getStringExtra("userid");
        server=intent.getStringExtra("server");
        if(userid.equals("11")){
        Toast.makeText(this, "I love you", Toast.LENGTH_LONG).show();}
        else{
            Toast.makeText(this, "Welcome, "+name, Toast.LENGTH_LONG).show();
        }
        Log.d("chech", "Service Started!");
        client =
                LocationServices.getFusedLocationProviderClient(this);
        lr=new LocationRequest();
        lr.setInterval(20000);
        lr.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        lr.setFastestInterval(10000);
        lc=new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if(locationResult==null){
                    return;
                }
                else{
                    for (Location location : locationResult.getLocations()) {
                        // Update UI with location data
                        // ...
                        latitude=location.getLatitude();
                        longitude=location.getLongitude();
                        updateDatabase();
                    }

                }
            }
        };

// Get the last known location
       /* client.getLastLocation()
                .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        // ...
                    }
                });*/

        /*lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        ll=new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                //Toast.makeText(LocationSenderService.this, "" + location.getLatitude(), Toast.LENGTH_LONG).show();
                latitude=location.getLatitude();
                longitude=location.getLongitude();
                updateDatabase();

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };*/
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        Permissions.check(this, permissions, null, null, new PermissionHandler() {
            @Override
            public void onGranted() {
                Log.d("chech","Permission Given!");
                //lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 300, 1f, ll );
                client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if(location!=null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            updateDatabase();

                        }
                        else{
                            Toast.makeText(LocationSenderService.this, "Null location", Toast.LENGTH_SHORT).show();
                        }
                        startLocationUpdates();
                    }
                });

            }
        });


        return START_REDELIVER_INTENT;
    }

    @SuppressLint("MissingPermission")
    public void startLocationUpdates() {
        client.requestLocationUpdates(lr,lc,null);
    }

    public void updateDatabase() {
        String url="http://"+server+"/LocUpdater.php?UserId="+userid+"&Latitude="+latitude+"&Longitude="+longitude;
        StringRequest req=new StringRequest(Request.Method.POST,url,new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {
                try {
                    Log.d("chech","Response received:"+response.toString());
                    JSONObject obj=new JSONObject(response);
                    Log.d("chech","object found");
                    String qs=obj.getString("qs");
                    if(qs.equals("true")){
                        Log.d("chech","updated database");
                    }
                    //Toast.makeText(MainActivity.this,"Welcome, "+name,Toast.LENGTH_LONG).show();
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
        RequestQueue rq= Volley.newRequestQueue(LocationSenderService.this);
        rq.add(req);
        req.setRetryPolicy(new DefaultRetryPolicy(5000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        startService(rootIntent);
    }
}
