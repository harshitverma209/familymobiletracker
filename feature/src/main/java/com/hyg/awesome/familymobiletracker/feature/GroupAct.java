package com.hyg.awesome.familymobiletracker.feature;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;

public class GroupAct extends Activity {
    ListView lv;
    String items[]={"c","d","e"};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        //items=new String[3];
        //items={"c","d","e"};
        lv=findViewById(R.id.lv);
        ArrayAdapter<String> groups=new ArrayAdapter<String>(GroupAct.this,android.R.layout.simple_list_item_1,items);
        lv.setAdapter(groups);
    }
}
