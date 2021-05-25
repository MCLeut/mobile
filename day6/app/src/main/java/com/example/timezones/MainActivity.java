package com.example.timezones;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static java.util.TimeZone.getDefault;

public class MainActivity extends AppCompatActivity {
    
    RecyclerView rv;
    public timezonesAdapter mAdapter;
    List<timezone> mTimezonesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialise list
        mTimezonesList= new ArrayList<>();

        for (String availableID : TimeZone.getAvailableIDs()) {
            mTimezonesList.add(new timezone(availableID));
        }

        rv = findViewById(R.id.recyclerView);
        mAdapter = new timezonesAdapter(this, mTimezonesList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(mLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(mAdapter);
    }


    // show time zone dialog
    public void showTimezoneTime(int adapterPosition) {

        showTimezoneDialog dialog = new showTimezoneDialog();
        dialog.setTimezone(mTimezonesList.get(adapterPosition));
        dialog.show(getSupportFragmentManager(), "");

    }
}