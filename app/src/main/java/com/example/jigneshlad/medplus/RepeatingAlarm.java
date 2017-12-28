package com.example.jigneshlad.medplus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RepeatingAlarm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repeating_alarm);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
