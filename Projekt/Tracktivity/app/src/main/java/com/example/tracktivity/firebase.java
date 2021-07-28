package com.example.tracktivity;

import android.location.Location;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class firebase {

    FirebaseDatabase database;
    FirebaseUser user;

    private static final String TAG = "firebase";

    public firebase(FirebaseUser user){
        this.user = user;

        this.database = FirebaseDatabase.getInstance();
    }

    public firebase(){
        this.user = null;

        this.database = FirebaseDatabase.getInstance();
    }

    String getUserName(){
        if (user != null) {
            return user.getDisplayName();
        }
        return "No user data provided.";
    }

    void insertGPSData(double lat, double lng, double alt,  float acc, float speed){
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String time = new SimpleDateFormat("HH:mm:ss:SSS", Locale.getDefault()).format(new Date());

        String path = "/locationData/"+date+"/";

        DatabaseReference myRef = database.getReference(path);
        myRef.child(time).child("lat").setValue(lat);
        myRef.child(time).child("lng").setValue(lng);
        myRef.child(time).child("alt").setValue(alt);
        myRef.child(time).child("speed").setValue(speed);
        myRef.child(time).child("acc").setValue(acc);

        startLocationDataUpdate(date);

        Log.d(TAG, "added data");

    }

    void insertGPSData(Location loc){
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String time = new SimpleDateFormat("HH:mm:ss:SSS", Locale.getDefault()).format(new Date());

        String path = "/locationData/"+date+"/";

        DatabaseReference myRef = database.getReference(path);
        myRef.child(time).child("lat").setValue(loc.getLatitude());
        myRef.child(time).child("lng").setValue(loc.getLongitude());
        myRef.child(time).child("alt").setValue(loc.getAltitude());
        myRef.child(time).child("speed").setValue(loc.getSpeed());
        myRef.child(time).child("acc").setValue(loc.getAccuracy());

        Log.d(TAG, "added data");

    }

    void startLocationDataUpdate(String date){
        database.getReference("/locationData/"+date+"/").get().addOnSuccessListener(ds -> {
            if (ds != null){
                List<LatLng> pathList = new ArrayList<>();
                for (DataSnapshot child : ds.getChildren()) {
                    LatLng point = new LatLng(
                            Double.parseDouble(child.child("lat").getValue().toString()),
                            Double.parseDouble(child.child("lng").getValue().toString()));

                    pathList.add(point);
                }
                if (pathList.toArray().length > 1) {
                    mapActivity.updateDailyPath(pathList);

                    // display startpoint of the day
                    if (mapActivity.date != null){
                        mapActivity.changeLocationMarker1Position(
                                pathList.get(0).latitude,
                                pathList.get(0).longitude
                        );
                        mapActivity.changeLocationMarker2Position(
                                pathList.get(pathList.size()-1).latitude,
                                pathList.get(pathList.size()-1).longitude
                        );
                    }
                }else{
                    Log.d(TAG, "couldn't find path data");
                }
            }
        });
    }

    void startLocationDataUpdate(){
        if(mapActivity.date == null) {
            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            startLocationDataUpdate(date);
        }else{
            startLocationDataUpdate(mapActivity.date);
        }
    }

    void updateDateSpinner(){
        database.getReference("/locationData/").get().addOnSuccessListener(ds -> {
            if (ds != null){
                List<String> dateList = new ArrayList<>();
                for (DataSnapshot child : ds.getChildren()) {
                    dateList.add(child.getKey());
                }
                if (dateList.toArray().length > 1) {
                    MainActivity.packDateSpinner(dateList);
                }else{
                    Log.d(TAG, "couldn't find dates");
                }
            }
        });
    }


    void logout(){
        FirebaseAuth.getInstance().signOut();
    }

}
