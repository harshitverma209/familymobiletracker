package com.hyg.awesome.familymobiletracker.feature;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hyg.awesome.familymobiletracker.feature.Adapters.MemberAdapter;
import com.hyg.awesome.familymobiletracker.feature.Models.MemberModel;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ViewMemberActivity extends AppCompatActivity {
    String userid,GroupId;
    ListView lv;
    FloatingActionButton add,showAll;
    MemberModel memberModel[];
    String GroupMemberName[];
    String GroupMemberId[];
    String server;

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewmember);

//        toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        showAll=findViewById(R.id.showAll);
        add=findViewById(R.id.add);
        lv=findViewById(R.id.lv);
        userid=getIntent().getStringExtra("userid");
        GroupId=getIntent().getStringExtra("GroupId");
        server=getIntent().getStringExtra("server");
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(ViewMemberActivity.this,AddMemberActivity.class);
                i.putExtra("userid",userid);
                i.putExtra("GroupId",GroupId);
                i.putExtra("server",server);
                finish();
                startActivityForResult(i,1);

            }
        });

        viewMembers();


    }

    public void viewMembers() {
        String url="http://"+server+"/ViewMembers.php?UserId="+userid+"&GroupId="+GroupId;
        StringRequest req=new StringRequest(Request.Method.POST,url,new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {
                try {
                    Log.d("chech","Response received:"+response.toString());
                    JSONObject obj=new JSONObject(response);
                    Log.d("chech","object found");
                    String qs=obj.getString("qs");
                    JSONArray GroupMemeberNames=obj.getJSONArray("GroupMemberNames");
                    JSONArray GroupMemberIds=obj.getJSONArray("GroupMemberIds");
                    GroupMemberId=new String[GroupMemberIds.length()];
                    GroupMemberName=new String[GroupMemeberNames.length()];
                    memberModel=new MemberModel[GroupMemberIds.length()];
                    for(int i=0;i<GroupMemberIds.length();i++){
                        memberModel[i]=new MemberModel();
                        GroupMemberName[i]=GroupMemeberNames.getString(i);
                        GroupMemberId[i]=Integer.toString(GroupMemberIds.getInt(i));
                        memberModel[i].setMemberName(GroupMemberName[i]);
                        memberModel[i].setPhoto(Integer.parseInt(GroupMemberId[i]));
                        Log.d("chech",GroupMemberName[i]);

                    }
                    Log.d("chech",qs);

                    if(qs.equals("true")) {

                        ListViewEnabler();
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
        RequestQueue rq= Volley.newRequestQueue(ViewMemberActivity.this);
        rq.add(req);
        req.setRetryPolicy(new DefaultRetryPolicy(5000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }

    public void ListViewEnabler() {
        MemberAdapter adapter=new MemberAdapter(ViewMemberActivity.this,memberModel);
        lv.setAdapter(adapter);
        showAllenabler();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(ViewMemberActivity.this,LocationAct.class);
                intent.putExtra("userid",userid);
                intent.putExtra("GroupId",GroupId);
                intent.putExtra("GroupMemberId",new String[]{GroupMemberId[i]})
                        .putExtra("GroupMemberName",new String[]{GroupMemberName[i]})
                        .putExtra("server",server);
                startActivity(intent);
            }
        });
    }

    public void showAllenabler() {
        showAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ViewMemberActivity.this,LocationAct.class);
                intent.putExtra("userid",userid);
                intent.putExtra("GroupId",GroupId);
                intent.putExtra("GroupMemberId",GroupMemberId)
                        .putExtra("GroupMemberName",GroupMemberName)
                        .putExtra("server",server);
                startActivity(intent);
            }
        });
    }

}

