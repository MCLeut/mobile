package com.example.tracktivity;

import android.location.Location;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

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
                }else{
                    Log.d(TAG, "couldn't find path data");
                }
            }
        });
    }

    void startLocationDataUpdate(){
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        startLocationDataUpdate(date);
    }



    void logout(){
        FirebaseAuth.getInstance().signOut();
    }

}
