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

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.Nullable;

public class AddMemberActivity extends Activity {
    EditText membername,username;
    String MName,MUniqueName;
    String userid,GroupId;
    Button add;
    String server;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmember);
        membername=findViewById(R.id.membername);
        username=findViewById(R.id.username);
        userid=getIntent().getStringExtra("userid");
        server=getIntent().getStringExtra("server");
        GroupId=getIntent().getStringExtra("GroupId");
        add=findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MName=membername.getText().toString();
                MUniqueName=username.getText().toString();
                addMember();
            }
        });

    }

    public void addMember() {
        String url="http://"+server+"/AddMember.php?UserId="+userid+"&GroupId="+GroupId+"&MemberName="+MName+"&MUniqueName="+MUniqueName;
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
                        Toast.makeText(AddMemberActivity.this, "Member " + MName + " Added", Toast.LENGTH_LONG).show();

                        Intent i=new Intent(AddMemberActivity.this,ViewMemberActivity.class);
                        i.putExtra("server",server);
                        i.putExtra("userid",userid);
                        finish();
                        startActivity(i);

                    }else if(qs.equals("No users by that username")){
                        Toast.makeText(AddMemberActivity.this,"No users by that username!",Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(AddMemberActivity.this,"Member Already Present",Toast.LENGTH_LONG).show();
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
        RequestQueue rq= Volley.newRequestQueue(AddMemberActivity.this);
        rq.add(req);
        req.setRetryPolicy(new DefaultRetryPolicy(5000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }
}
