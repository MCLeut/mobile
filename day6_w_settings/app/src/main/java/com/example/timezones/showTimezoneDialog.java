package com.example.timezones;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.TimeZone;

public class showTimezoneDialog extends DialogFragment {

    timezone mTimezone;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // show specific timezone layout
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.show_timezone_dialog, null);

        // target time zone utc offset in milliseconds
        int rawOffset = TimeZone.getTimeZone(
                mTimezone.getTitle()).getRawOffset();
        // local time zone utc offset in milliseconds
        int localRawOffset = TimeZone.getDefault().getRawOffset();
        // local time epoch time in seconds
        long epochTime = Instant.now().getEpochSecond();

        Date d = new Date(epochTime * 1000 + (rawOffset - localRawOffset));
        DateFormat df = new SimpleDateFormat("dd MMM yyyy HH:mm");

        // textview displays the time in the chosen timezone
        TextView showTimezoneTimeTextView = dialogView.findViewById(R.id.showTimezoneTimeTextView);
        showTimezoneTimeTextView.setText(df.format(d));

        // textview displays the name of the chosen timezone
        TextView showTimezoneNameTextView = dialogView.findViewById(R.id.showTimezoneNameTextView);
        showTimezoneNameTextView.setText(mTimezone.getTitle());

        // textview displays the converted time from the timepicker
        TextView convertedTimeTextView = dialogView.findViewById(R.id.convertedTimeTextView);

        // make timepicker work for flexible time conversion
        TimePicker timePicker = dialogView.findViewById(R.id.timePicker1);
        // set timepicker to the one and only 24h format
        timePicker.setIs24HourView(true);
        timePicker.setOnTimeChangedListener((view, hourOfDay, minute) -> {
            // target time zone utc offset in milliseconds
            int rawOffset1 = TimeZone.getTimeZone(
                    mTimezone.getTitle()).getRawOffset();
            // local time zone utc offset in milliseconds
            int localRawOffset1 = TimeZone.getDefault().getRawOffset();
            // simple way of converting timepicker output into usable "epoch time"
            long epochTime1 = hourOfDay * 3600 + minute * 60;

            Date d1 = new Date(epochTime1 * 1000 + (rawOffset1 - localRawOffset1));
            DateFormat df1 = new SimpleDateFormat("HH:mm");

            convertedTimeTextView.setText(df1.format(d1));
        });


        // "Back to list" button dismisses the dialog
        Button returnButton = dialogView.findViewById(R.id.returnButton);
        returnButton.setOnClickListener(v -> dismiss());

        builder.setView(dialogView).setMessage("Timezone:");

        return builder.create();
    }

    public void setTimezone(timezone timezone){
        mTimezone = timezone;
    }
}
