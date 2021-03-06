package com.example.tracktivity;

import android.annotation.SuppressLint;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;


@SuppressLint("SpecifyJobSchedulerIdRange")
public class gpsDataJob extends JobService {

    private static final String TAG = "gpsJobService";
    private final LocationListener mLocationListener = location -> {
        // nothing
    };
    firebase fb = new firebase();

    @Override
    public boolean onStartJob(JobParameters params) {
        Location loc = getLocation();

        if (loc != null) {
            fb.insertGPSData(loc);
        }

        Util.scheduleJob(getApplicationContext()); // reschedule the job
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    public Location getLocation() {
        Location location = null;
        LocationManager mLocationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);

        // getting GPS & network location status
        boolean isGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSEnabled && !isNetworkEnabled) {
            // no network provider is enabled
            Log.d(TAG, "no network provided");
        } else {
            if (isGPSEnabled) {
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, (long) 1000, (float) 1, mLocationListener);
                Log.d(TAG, "GPS Enabled");
                // no null check needed
                location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            } else if (isNetworkEnabled) {
                mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, (long) 1000, (float) 0, mLocationListener);
                Log.d(TAG, "Network");
                // no null check needed
                location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
            //get the location by gps

        }

        return location;
    }
}
