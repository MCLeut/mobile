package com.example.tracktivity;

import android.location.Location;
import android.location.LocationListener;

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
    public void onProviderDisabled(String provider) {}

    @Override
    public void onProviderEnabled(String provider) {}

}
