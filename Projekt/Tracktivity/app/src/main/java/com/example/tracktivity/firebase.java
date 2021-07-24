package com.example.tracktivity;

import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class firebase {

    FirebaseDatabase database;
    FirebaseUser user;

    public firebase(FirebaseUser user){
        this.user = user;

        database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference("message");
        myRef.child("users").child("22324").setValue("xyz");

        myRef.child("users").child("22324").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            }
            else {
                if (task.getResult().getValue() != null) {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                }else {
                    Log.d("firebase", "Value is null!");
                }
            }
        });
    }

    String getUserName(){
        return user.getDisplayName();
    }

    void logout(){
        FirebaseAuth.getInstance().signOut();
    }

}
