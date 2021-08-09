package com.hyg.awesome.familymobiletracker.feature;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddGroupActivity extends AppCompatActivity {
    EditText groupid,groupname;
    Button add;
    String gname,gid;
    String userid;
    String server;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addgroup);
        groupid=findViewById(R.id.groupid);
        groupname=findViewById(R.id.groupname);
        add=findViewById(R.id.add);
        userid=getIntent().getStringExtra("userid");
        server=getIntent().getStringExtra("server");
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gname=groupname.getText().toString();
                gid=groupid.getText().toString();
                addGroup();

            }
        });
    }
    public void addGroup(){
        String url="http://"+server+"/AddGroup.php?UserId="+userid+"&GUniqueName="+gid+"&GroupName="+gname;
        StringRequest req=new StringRequest(Request.Method.POST,url,new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {
                try {
                    Log.d("chech","Response received:"+response.toString());
                    JSONObject obj=new JSONObject(response);
                    Log.d("chech","object found");
                   String qs=obj.getString("qs");

                   Log.d("chech",qs);

                    if(qs.equals("true")) {
                        Toast.makeText(AddGroupActivity.this, "Group " + gname + " Added", Toast.LENGTH_LONG).show();

                        Intent i=new Intent(AddGroupActivity.this,ViewGroupActivity.class);
                        i.putExtra("server",server);
                        i.putExtra("userid",userid);
                        finish();
                        startActivity(i);

                    }else{
                        Toast.makeText(AddGroupActivity.this,"Group ID taken",Toast.LENGTH_LONG).show();
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
        RequestQueue rq= Volley.newRequestQueue(AddGroupActivity.this);
        rq.add(req);
        req.setRetryPolicy(new DefaultRetryPolicy(5000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }
}
