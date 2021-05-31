package com.example.timezones;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
    
    RecyclerView rv;
    public timezonesAdapter mAdapter;
    List<timezone> mTimezonesList;

    private SharedPreferences mPrefs;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPrefs = getSharedPreferences("timezones", MODE_PRIVATE);
        mEditor = mPrefs.edit();

        setDefaultNightMode();

        // liste initialisieren
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


    // zeitzonen dialog (mit konverter) einblenden
    public void showTimezoneTime(int adapterPosition) {

        showTimezoneDialog dialog = new showTimezoneDialog();
        dialog.setTimezone(mTimezonesList.get(adapterPosition));
        dialog.show(getSupportFragmentManager(), "");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setDefaultNightMode(){
        boolean followSystem  = mPrefs.getBoolean("followSystem", false);
        boolean dayNightMode  = mPrefs.getBoolean("dayNight", false);

        if(followSystem){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }else {
            if (dayNightMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        }
    }
}