package com.example.tracktivity;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class firebase {

    FirebaseDatabase database;
    FirebaseUser user;

    public firebase(FirebaseUser user){
        this.user = user;

        this.database = FirebaseDatabase.getInstance();
    }

    String getUserName(){
        return user.getDisplayName();
    }

    void insertGPSData(double lat, double lng, double alt,  float acc, float speed){
        Calendar calInstance = Calendar.getInstance();
        String y = String.valueOf(calInstance.get(Calendar.YEAR));
        String M = String.valueOf(calInstance.get(Calendar.MONTH) + 1); // zero based - more human readable
        String d = String.valueOf(calInstance.get(Calendar.DAY_OF_MONTH));
        String h = String.valueOf(calInstance.get(Calendar.HOUR_OF_DAY));
        String m = String.valueOf(calInstance.get(Calendar.MINUTE));
        String s = String.valueOf(calInstance.get(Calendar.SECOND));
        String ms = String.valueOf(calInstance.get(Calendar.MILLISECOND));

        String path = "/locationData/"+y+"-"+M+"-"+d+"/";

        String time = h+":"+m+":"+s+":"+ms;


        DatabaseReference myRef = database.getReference(path);
        myRef.child(time).child("lat").setValue(lat);
        myRef.child(time).child("lng").setValue(lng);
        myRef.child(time).child("alt").setValue(alt);
        myRef.child(time).child("speed").setValue(speed);
        myRef.child(time).child("acc").setValue(acc);

        //startLocationDataUpdate(y+"-"+M+"-"+d);

        Log.d("firebase", "added data");

    }

    void startLocationDataUpdate(String date){
        database.getReference("/locationData/"+date+"/").get().addOnSuccessListener(ds -> {
            if (ds != null){
                List<LatLng> pathList;
                for (DataSnapshot child : ds.getChildren()) {
                    Log.d("firebase", child.getKey());
                }

                //mapActivity.updateDailyPath(pathList);
            }
        });
    }

    void startLocationDataUpdate(){
        Calendar calInstance = Calendar.getInstance();
        String y = String.valueOf(calInstance.get(Calendar.YEAR));
        String M = String.valueOf(calInstance.get(Calendar.MONTH) + 1); // zero based - more human readable
        String d = String.valueOf(calInstance.get(Calendar.DAY_OF_MONTH));

        database.getReference("/locationData/"+y+"-"+M+"-"+d+"/").get().addOnSuccessListener(ds -> {
            if (ds != null){
                List<LatLng> pathList = new ArrayList<>();
                for (DataSnapshot child : ds.getChildren()) {
                    LatLng point = new LatLng(
                            Double.parseDouble(child.child("lat").getValue().toString()),
                            Double.parseDouble(child.child("lng").getValue().toString()));
                    
                    pathList.add(point);
                }

                mapActivity.updateDailyPath(pathList);
            }
        });
    }



    void logout(){
        FirebaseAuth.getInstance().signOut();
    }

}
