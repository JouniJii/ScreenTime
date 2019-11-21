package com.example.screentime;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.Calendar;

public class OmaReceiver extends BroadcastReceiver {

    final static String SCREEN_ON = "Screen on";
    final static String SCREEN_OFF = "Screen off";

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            Toast.makeText(context,SCREEN_OFF, Toast.LENGTH_LONG).show();
            saveAction(context, SCREEN_OFF);

        } else
            if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            Toast.makeText(context, SCREEN_ON, Toast.LENGTH_LONG).show();
            saveAction(context, SCREEN_ON);
        }
    }

    // Ask IntentPalvelu to save this action and timestamp to the database.
    public void saveAction(Context context, String action) {
        String time = Calendar.getInstance().getTime().toString();
        Intent omaIntent = new Intent();
        omaIntent.setAction(IntentPalvelu.ACTION_SAVE_TIMESTAMP);
        omaIntent.putExtra(IntentPalvelu.EXTRA_KEY_ON_OFF, action);
        omaIntent.putExtra(IntentPalvelu.EXTRA_KEY_TIME, time);
        omaIntent.setClass(context, IntentPalvelu.class);
        context.startService(omaIntent);
    }
}
