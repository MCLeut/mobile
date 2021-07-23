package com.example.tracktivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.snackbar.Snackbar;

public class checkPermissionsActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_FINE_LOCATION = 1000;
    private static final int PERMISSION_READ_PHONE_STATE = 1001;

    private View mLayout;

    private TextView statusTV;
    private Button grantPermissionsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.permission_activity);

        mLayout = findViewById(R.id.permission_activity_layout);

        statusTV = findViewById(R.id.checkPermissionsStatusTextView);
        grantPermissionsButton = findViewById(R.id.grantPermissionsButton);

        grantPermissionsButton.setOnClickListener(v -> {
            requestFineLocationPermission();
        });

        checkPermissions();
    }

    private void checkPermissions(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            // Permission is already available, inform user
            statusTV.setText("Thanks for granting the fine location access permission!");
            Snackbar.make(mLayout,
                    "The Permission for accessing fine location is available!",
                    Snackbar.LENGTH_SHORT).show();
        } else {
            // Permission is missing and must be requested.
            statusTV.setText("You need the access fine location permission!");
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                == PackageManager.PERMISSION_GRANTED) {
            // Permission is already available, inform user (with new line)
            statusTV.setText(statusTV.getText() + "\nThanks for granting the read phone state permission!");
            Snackbar.make(mLayout,
                    "The Permission for reading the phone state is available!",
                    Snackbar.LENGTH_SHORT).show();

            Intent signInIntent = new Intent(this, googleSignInActivity.class);
            startActivity(signInIntent);
        } else {
            // Permission is missing and must be requested.
            statusTV.setText(statusTV.getText() + "\nYou need the read phone state permission!");
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSION_REQUEST_FINE_LOCATION:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Snackbar.make(mLayout, "Permission Granted!", Snackbar.LENGTH_SHORT).show();

                    requestReadPhoneStatePermission();
                } else {
                    Snackbar.make(mLayout, "Permission Denied! Restart the app to try again", Snackbar.LENGTH_SHORT).show();
                }

                checkPermissions();
                break;
            case PERMISSION_READ_PHONE_STATE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Snackbar.make(mLayout, "Permission Granted!", Snackbar.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(mLayout, "Permission Denied!", Snackbar.LENGTH_SHORT).show();
                }

                checkPermissions();
                break;
        }

    }

    private void requestFineLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            requestReadPhoneStatePermission();
        }else {
            // Permission has not been granted and must be requested.
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                Snackbar.make(mLayout, "The Fine Location Permission is needed for the app to function.",
                        Snackbar.LENGTH_INDEFINITE).setAction("Okay!", view -> {
                    // Request the permission
                    ActivityCompat.requestPermissions(checkPermissionsActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_FINE_LOCATION);
                }).show();

            } else {
                // Request the permission. The result will be received in onRequestPermissionResult().
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_FINE_LOCATION);
            }
        }
    }

    private void requestReadPhoneStatePermission() {
        // Permission has not been granted and must be requested.
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_PHONE_STATE)) {
            Snackbar.make(mLayout, "The Read Phone State Permission is needed for the app to function.",
                    Snackbar.LENGTH_INDEFINITE).setAction("Okay!", view -> {
                // Request the permission
                ActivityCompat.requestPermissions(checkPermissionsActivity.this,
                        new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSION_READ_PHONE_STATE);
            }).show();

        } else {
            // Request the permission. The result will be received in onRequestPermissionResult().
            Snackbar.make(mLayout, "Requesting...",
                    Snackbar.LENGTH_INDEFINITE).show();
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSION_READ_PHONE_STATE);
        }
    }


}
