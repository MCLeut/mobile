package com.example.tracktivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

public class mapActivity extends AppCompatActivity implements OnMapReadyCallback {

    public static String date = null;
    static Marker locationMarker1;
    static Marker locationMarker2;
    static Polyline dailyPath;

    public static void updateDailyPath(List<LatLng> pathList) {
        dailyPath.setPoints(pathList);
    }

    public static void changeLocationMarker1Position(double lat, double lng) {
        locationMarker1.setPosition(new LatLng(lat, lng));

        if (date == null) {
            locationMarker1.setTitle("Aktuelle Position");
        } else {
            locationMarker1.setTitle("Startpunkt");
        }
    }

    public static void changeLocationMarker2Position(double lat, double lng) {
        locationMarker2.setPosition(new LatLng(lat, lng));

        if (date == null) {
            locationMarker2.setAlpha(0); // invisible
        } else {
            locationMarker2.setAlpha(1); // visible
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the layout file as the content view.
        setContentView(R.layout.activity_map);

        // Get a handle to the fragment and register the callback.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        // Handle location
        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new locationListener();

        // check for permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 5000, 10, locationListener);

    }

    // Get a handle to the GoogleMap object and display marker.
    @Override
    public void onMapReady(GoogleMap googleMap) {
        locationMarker1 = googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(0, 0))
        );

        locationMarker2 = googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(0, 0))
                .title("Endpunkt")
        );

        dailyPath = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
        );

        // over europe, temporary
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(54.5260, 15.2551), 5)
        );

    }

}
