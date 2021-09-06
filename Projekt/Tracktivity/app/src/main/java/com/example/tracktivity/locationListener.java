package com.example.tracktivity;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import androidx.annotation.NonNull;

public class locationListener implements LocationListener {

    firebase fb = new firebase();

    @Override
    public void onLocationChanged(Location loc) {

        mapActivity.changeLocationMarker1Position(loc.getLatitude(), loc.getLongitude());

        /* not used, replaced by gps job service
        fb.insertGPSData(
                loc.getLatitude(), loc.getLongitude(), loc.getAltitude(), loc.getAccuracy(), loc.getSpeed()
        );
        */

        fb.startLocationDataUpdate();
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

}
