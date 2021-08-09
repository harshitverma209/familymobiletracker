package com.hyg.awesome.familymobiletracker.feature;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class NavDrawerActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_drawer);
        TextView settings = findViewById(R.id.settings);
        settings.setText("gggggg");
    }
}
