package com.example.tracktivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    TextView tv;

    firebase fb;

    private View mLayout;
    private Button locationOnMapButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLayout = findViewById(R.id.mainActivity_layout);

        locationOnMapButton = findViewById(R.id.showLocationOnMapButton);

        fb = new firebase(googleSignInActivity.fbUser);

        tv = findViewById(R.id.textView1);
        tv.setText("Willkommen, " + fb.getUserName());

        locationOnMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapActivityIntent = new Intent(getApplicationContext(), mapActivity.class);
                startActivity(mapActivityIntent);
            }
        });

    }

    @Override
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