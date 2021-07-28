package com.example.tracktivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static firebase fb;
    static ArrayAdapter<String> adapter;
    TextView tv;
    String selectedDate = "";
    private View mLayout;

    public static void packDateSpinner(List<String> dateList) {
        for (String date : dateList) {
            adapter.add(date);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fb = new firebase(googleSignInActivity.fbUser);


        mLayout = findViewById(R.id.mainActivity_layout);

        tv = findViewById(R.id.textView1);
        tv.setText("Willkommen, " + fb.getUserName() + "!");


        Spinner sItems = findViewById(R.id.selectDateSpinner);

        List<String> spinnerArray = new ArrayList<>();
        adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sItems.setAdapter(adapter);
        sItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDate = adapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedDate = "";
            }
        });

        fb.updateDateSpinner();

        findViewById(R.id.showLocationOnMapButton).setOnClickListener(v -> {
            Intent mapActivityIntent = new Intent(getApplicationContext(), mapActivity.class);
            mapActivity.date = null;
            startActivity(mapActivityIntent);
        });

        findViewById(R.id.button2).setOnClickListener(v -> {
            if (!selectedDate.equals("")) {
                Intent mapActivityIntent = new Intent(getApplicationContext(), mapActivity.class);
                mapActivity.date = selectedDate;
                startActivity(mapActivityIntent);
            } else {
                Snackbar.make(mLayout,
                        "Bitte ein Datum auswählen!",
                        Snackbar.LENGTH_SHORT).show();
            }
        });

        Util.scheduleJob(this);

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