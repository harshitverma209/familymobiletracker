package com.hyg.awesome.familymobiletracker.feature;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.hyg.awesome.familymobiletracker.feature.Adapters.GroupAdapter;
import com.hyg.awesome.familymobiletracker.feature.Models.GroupModel;

import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ViewGroupActivity extends AppCompatActivity {
    AlertDialog alertDialog;
    SwipeMenuListView lv;
    //DrawerLayout dl;
    FloatingActionButton add;
    String userid;
    String GroupName[];
    GroupModel group[];
    Toolbar toolbar;
    int GroupId[],Gnom[];
    String server;
    NavigationView nv;
    Menu nav_menu;
    SwipeMenuCreator creator;
    int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewgroup);

        lv=findViewById(R.id.lv);
        //dl=findViewById(R.id.dl);
        add=findViewById(R.id.add);

        //toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        userid=getIntent().getStringExtra("userid");
        server=getIntent().getStringExtra("server");
        Log.d("chech","userid:"+userid);

        //String groups[]={"G1","G2","G3"};
        swipeCreator();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(ViewGroupActivity.this,AddGroupActivity.class);
                i.putExtra("userid",userid);
                i.putExtra("server",server);
                finish();
                startActivityForResult(i,1);
            }
        });
        viewGroups();

        navDrawerEnabler();
    }

    public void navDrawerEnabler(){
    /*    final ListView navList=(slidingRootNavLayout.findViewById(R.id.navList));
        String navItems[]={"About"};
        ArrayAdapter<String> navAdapter=new ArrayAdapter<String>(ViewGroupActivity.this,android.R.layout.simple_list_item_1,navItems);
        navList.setAdapter(navAdapter);
        navList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(((TextView)view).getText().equals("About")){
                    slidingRootNav.closeMenu(true );
                    AlertDialog alertDialog=new AlertDialog.Builder(ViewGroupActivity.this).create();
                    alertDialog.setTitle("About Us");
                    alertDialog.setMessage("Creators:\nHarshit Verma,\nChahat Sharma");
                    alertDialog.show();
                }
            }
        });
        */
//        Drawer.OnDrawerItemClickListener drawerItemClickListener = new Drawer.OnDrawerItemClickListener() {
//            @Override
//            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
//                    if(drawerItem.getIdentifier()==1){
//                        AlertDialog alertDialog=new AlertDialog.Builder(ViewGroupActivity.this).create();
//                        alertDialog.setTitle("About Us");
//                        alertDialog.setMessage("Creators:\nHarshit Verma,\nChahat Sharma");
//                        alertDialog.show();
//                    }
//                return true;
//            }
//        };
//        PrimaryDrawerItem about = new PrimaryDrawerItem().withIdentifier(1)
//                .withIcon(R.drawable.ic_about).withName("About");
//        Drawer drawer=new DrawerBuilder().withActivity(this).withToolbar(toolbar)
//                .addDrawerItems(about,new DividerDrawerItem())
//                .withOnDrawerItemClickListener(drawerItemClickListener)
//                .build();
    }
    public void swipeCreator() {
        creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                //openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                 //       0xCE)));
                // set item width
                //openItem.setWidth(90);
                // set item title
                //openItem.setTitle("Open");
                // set item title fontsize
                //openItem.setTitleSize(18);
                // set item title font color
                //openItem.setTitleColor(Color.WHITE);
                // add to menu
                //menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem renameItem = new SwipeMenuItem(
                        getApplicationContext());
                renameItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                               0xCE)));
                // set item width

                renameItem.setWidth((120));
                // set a icon
                renameItem.setIcon(R.drawable.ic_rename);
                // add to menu
                menu.addMenuItem(renameItem);
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width

                deleteItem.setWidth((120));
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);

            }
        };

// set creator
        lv.setMenuCreator(creator);
        lv.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        // rename
                        LayoutInflater li = LayoutInflater.from(ViewGroupActivity.this);
                        View renameView = li.inflate(R.layout.rename_dialog, null);
                        final EditText editText=renameView.findViewById(R.id.editText);

                        pos=position;
                        alertDialog=new AlertDialog.Builder(ViewGroupActivity.this)
                                .setTitle("Rename Group")
                                .setView(renameView)
                                .setCancelable(true)
                                .setPositiveButton("Rename", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        String newGname=editText.getText().toString();
                                        renameGroup(pos,newGname);
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                })
                                .create();
                        alertDialog.show();
                        break;
                    case 1:
                        // delete
                        pos=position;
                                alertDialog=new AlertDialog.Builder(ViewGroupActivity.this)
                                .setTitle("Are you sure?")
                                .setMessage("Are you sure you want to exit the group:"+GroupName[pos])
                                .setCancelable(true)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        deleteGroup(pos);
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                })
                                .create();
                        alertDialog.show();
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
        lv.setCloseInterpolator(new BounceInterpolator());
// Open Interpolator
        lv.setOpenInterpolator(new AnticipateOvershootInterpolator());
    }

    public void renameGroup(int position,String newGname) {
        if(newGname==null){
            Toast.makeText(this, "Group Name can't be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        int GroupId=group[position].getGroupId();
        Log.d("chech",group[position].getGroupName());


        String url="http://"+server+"/RenameGroup.php?UserId="+userid+"&GroupId="+GroupId+"&NewName="+newGname;
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
                        Toast.makeText(ViewGroupActivity.this, "Renamed Group", Toast.LENGTH_SHORT).show();
                        viewGroups();
                        //Toast.makeText(ViewGroupActivity.this, "Group " + gname + " Added", Toast.LENGTH_LONG).show();
                        //finish();
                    }else{
                        Toast.makeText(ViewGroupActivity.this,"Error",Toast.LENGTH_LONG).show();
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
        RequestQueue rq= Volley.newRequestQueue(ViewGroupActivity.this);
        rq.add(req);
        req.setRetryPolicy(new DefaultRetryPolicy(5000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }

    public void deleteGroup( int position) {
        int GroupId=group[position].getGroupId();
        Log.d("chech",group[position].getGroupName());
        Toast.makeText(this, "Delete Group", Toast.LENGTH_SHORT).show();
        String url="http://"+server+"/DeleteGroup.php?UserId="+userid+"&GroupId="+GroupId;
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

                        viewGroups();
                        //Toast.makeText(ViewGroupActivity.this, "Group " + gname + " Added", Toast.LENGTH_LONG).show();
                        //finish();
                    }else{
                        Toast.makeText(ViewGroupActivity.this,"Error",Toast.LENGTH_LONG).show();
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
        RequestQueue rq= Volley.newRequestQueue(ViewGroupActivity.this);
        rq.add(req);
        req.setRetryPolicy(new DefaultRetryPolicy(5000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }


    public void viewGroups() {
        String url="http://"+server+"/ViewGroups.php?UserId="+userid;
        StringRequest req=new StringRequest(Request.Method.POST,url,new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {
                try {
                    Log.d("chech","Response received:"+response.toString());
                    JSONObject obj=new JSONObject(response);
                    Log.d("chech","object found");
                    String qs=obj.getString("qs");
                    JSONArray GroupNames=obj.getJSONArray("GroupNames");
                    JSONArray GroupIds=obj.getJSONArray("GroupIds");
                    JSONArray Gnoms=obj.getJSONArray("Gnoms");
                    Gnom = new int[Gnoms.length()];
                    GroupId=new int[GroupIds.length()];
                    GroupName=new String[GroupNames.length()];
                    group=new GroupModel[GroupIds.length()];
                    for(int i=0;i<GroupNames.length();i++){
                        group[i]=new GroupModel();
                        GroupName[i]=GroupNames.getString(i);
                        GroupId[i]=GroupIds.getInt(i);
                        Gnom[i]=Gnoms.getInt(i);
                        group[i].setGroupName(GroupName[i]);
                        group[i].setGroupId(GroupId[i]);
                        group[i].setGnom(Gnom[i]);
                        //Log.d("chech",GroupName[i]);

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
        RequestQueue rq= Volley.newRequestQueue(ViewGroupActivity.this);
        rq.add(req);
        req.setRetryPolicy(new DefaultRetryPolicy(5000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }

    public void ListViewEnabler() {
        GroupAdapter adapter=new GroupAdapter(ViewGroupActivity.this,group);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(ViewGroupActivity.this,ViewMemberActivity.class);
                intent.putExtra("userid",userid);
                Log.d("chech","Group Selected:"+GroupId[i]+", Group Name:"+GroupName[i]);
                intent.putExtra("GroupId",Integer.toString(GroupId[i]));
                intent.putExtra("server",server);
                startActivity(intent);
            }
        });
    }

//    @Override
//    public void onBackPressed() {
//
//        if (dl.isDrawerOpen(GravityCompat.START)) {
//            dl.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }

}
