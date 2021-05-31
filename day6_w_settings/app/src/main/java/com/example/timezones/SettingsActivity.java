package com.example.timezones;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class SettingsActivity extends AppCompatActivity {

    private SharedPreferences mPrefs;
    private SharedPreferences.Editor mEditor;

    private boolean followSystem;
    private boolean dayNightMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mPrefs = getSharedPreferences("timezones", MODE_PRIVATE);
        mEditor = mPrefs.edit();

        followSystem  = mPrefs.getBoolean("followSystem", true);
        dayNightMode  = mPrefs.getBoolean("dayNight", false);

        Switch followSystemSwitch = findViewById(R.id.dayNightFollowSystemSwitch);
        Switch dayNightSwitch = findViewById(R.id.dayNightSwitch);

        // switches nach den gespeicherten shared preferences setzen
        followSystemSwitch.setChecked(followSystem);
        dayNightSwitch.setChecked(dayNightMode);

        // dayNightSwitch nicht auswählbar und ausgegraut machen
        if(followSystem){
            dayNightSwitch.setClickable(false);
            dayNightSwitch.setAlpha(0.5f);
        }else{
            dayNightSwitch.setClickable(true);
            dayNightSwitch.setAlpha(1f);
        }

        followSystemSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {

                if(isChecked){
                    mEditor.putBoolean(
                            "followSystem", true);
                    followSystem = true;

                    dayNightSwitch.setClickable(false);

                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);

                }else{
                    mEditor.putBoolean(
                            "followSystem", false);
                    followSystem = false;

                    dayNightSwitch.setClickable(true);

                    if (dayNightMode){
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        mEditor.putBoolean(
                                "dayNight", true);
                    }else{
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        mEditor.putBoolean(
                                "dayNight", false);
                    }

                }

                // nicht elegant, verbraucht wahrscheinlich viel ram, vereinfacht aber alles sehr
                // mit profiler getestet, jeder Start verbraucht etwa 3MB, man kann es ignorieren
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
        });

        dayNightSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    mEditor.putBoolean(
                            "dayNight", true);
                }else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    mEditor.putBoolean(
                            "dayNight", false);
                }

                // nicht elegant, verbraucht wahrscheinlich viel ram, vereinfacht aber alles sehr
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
        });

    }

    @Override
    protected void onPause() {
        super.onPause();

        // einstellungen speichern (wird beim ausführen der neuen mainactivity ausgeführt, etc.)
        mEditor.commit();
    }


}
