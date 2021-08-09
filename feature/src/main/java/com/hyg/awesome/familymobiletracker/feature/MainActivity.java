package com.hyg.awesome.familymobiletracker.feature;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dd.processbutton.iml.ActionProcessButton;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    String url,server;
    EditText username,password;
    TextView tx,tx2;
    ActionProcessButton log,signup;
    String name,userid;
    String qs="false";
    //ActionProcessButton login;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username=findViewById(R.id.editText);
        password=findViewById(R.id.editText2);
        tx=findViewById(R.id.textView);
        tx2=findViewById(R.id.textView2);
        log=findViewById(R.id.button);
        signup=findViewById(R.id.signup);
        log.setMode(ActionProcessButton.Mode.ENDLESS);
        //signup.setMode();


        //server="192.168.1.6/project";
        server="familymobiletracker.dx.am/php";



        final Intent i=new Intent(MainActivity.this,GroupAct.class);
         log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                log.setProgress(50);
                log.setText("Logging In");
                //log.setProgress();
                userLogin();
            }
        });
         signup.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent i=new Intent(MainActivity.this,SignupActivity.class);
                 i.putExtra("server",server);
                 startActivity(i);
             }
         });
    }

public void userLogin(){
    url="http://"+server+"/Login.php?username="+username.getText().toString()+"&password="+password.getText().toString();

    StringRequest req=new StringRequest(Request.Method.POST,url,new Response.Listener<String>(){

        @Override
        public void onResponse(String response) {
            try {
                Log.d("chech","Response received:"+response.toString());
                JSONObject obj=new JSONObject(response);
                Log.d("chech","object found");
                userid=obj.getString("userid");
                name=obj.getString("name");
                Log.d("chech",userid);
                qs=obj.getString("qs");
                //Toast.makeText(MainActivity.this,"Welcome, "+name,Toast.LENGTH_LONG).show();

            } catch (JSONException e) {
                Log.d("chech","string not found");
                e.printStackTrace();
            }
            if(qs.equals("true")){
                log.setText("Success");
                log.setProgress(100);
                startLocationSenderService();
                Intent i=new Intent(MainActivity.this,ViewGroupActivity.class);
                i.putExtra("server",server);
                i.putExtra("userid",userid);
                startActivity(i);
            }else{
                Toast.makeText(MainActivity.this,"Error, Please try again ",Toast.LENGTH_LONG).show();
                log.setText("Try Again");
                log.setProgress(-1);
            }
        }
    },new Response.ErrorListener(){

        @Override
        public void onErrorResponse(VolleyError error) {
        Log.d("chech",error.toString());
        }
    });
    RequestQueue rq= Volley.newRequestQueue(MainActivity.this);
    rq.add(req);
    req.setRetryPolicy(new DefaultRetryPolicy(5000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

}

    public void startLocationSenderService() {
        Intent i=new Intent(MainActivity.this,LocationSenderService.class);
        i.putExtra("name",name);
        i.putExtra("userid",userid);
        i.putExtra("server",server);
        startService(i);
    }
}
