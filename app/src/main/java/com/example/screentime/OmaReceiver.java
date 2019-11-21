package com.example.screentime;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

public class OmaReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            Toast.makeText(context, "Screen off.", Toast.LENGTH_LONG).show();
            saveAction(context, "ACTION_SCREEN_OFF");

        } else
            if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            Toast.makeText(context, "Screen on.", Toast.LENGTH_LONG).show();
            saveAction(context, "ACTION_SCREEN_ON");
        }
    }

    // Ask IntentPalvelu to save this action and timestamp to the database.
    public void saveAction(Context context, String action) {
        String time = Calendar.getInstance().getTime().toString();
        Intent omaIntent = new Intent();
        Log.i("OMA", action);
        omaIntent.setAction(IntentPalvelu.ACTION_SAVE_TIMESTAMP);
        omaIntent.putExtra(IntentPalvelu.EXTRA_KEY_ON_OFF, action);
        omaIntent.putExtra(IntentPalvelu.EXTRA_KEY_TIME, time);
        omaIntent.setClass(context, IntentPalvelu.class);
        context.startService(omaIntent);
    }

}
