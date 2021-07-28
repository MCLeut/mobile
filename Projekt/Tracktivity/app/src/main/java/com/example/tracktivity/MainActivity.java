package com.example.tracktivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    TextView tv;

    public static firebase fb;

    private View mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLayout = findViewById(R.id.mainActivity_layout);

        Button locationOnMapButton = findViewById(R.id.showLocationOnMapButton);

        fb = new firebase(googleSignInActivity.fbUser);

        tv = findViewById(R.id.textView1);
        tv.setText("Willkommen, " + fb.getUserName());

        locationOnMapButton.setOnClickListener(v -> {
            Intent mapActivityIntent = new Intent(getApplicationContext(), mapActivity.class);
            startActivity(mapActivityIntent);
        });

        Util.scheduleJob(getApplicationContext());

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainactivity_optionsmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {
            Snackbar.make(mLayout, "Wollen Sie sich wirklich abmelden?",
                    Snackbar.LENGTH_LONG).setAction("Ja!", view -> {
                        fb.logout();
                        Intent signInIntent = new Intent(this, googleSignInActivity.class);
                        startActivity(signInIntent);
            }).show();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}