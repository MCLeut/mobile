package com.example.tracktivity;

import android.location.Location;
import android.location.LocationListener;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class locationListener implements LocationListener {

    MainActivity mainActivity;

    @Override
    public void onLocationChanged(Location loc) {
        /*gpsActivity.setTextViewText(
                "Location changed: Lat: " + loc.getLatitude() + " Lng: "
                        + loc.getLongitude());
        */

        mapActivity.changeCurrentPosition(loc.getLatitude(), loc.getLongitude());

        mainActivity.fb.insertGPSData(
                loc.getLatitude(), loc.getLongitude(), loc.getAltitude(), loc.getAccuracy(), loc.getSpeed()
        );

        mainActivity.fb.startLocationDataUpdate();
    }

    @Override
    public void onProviderDisabled(String provider) {}

    @Override
    public void onProviderEnabled(String provider) {}

}
