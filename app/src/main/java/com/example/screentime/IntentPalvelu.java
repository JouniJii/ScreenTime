package com.example.screentime;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.example.screentime.db.ItemDao;
import com.example.screentime.db.ItemDatabase;
import com.example.screentime.db.ItemEntity;

import java.util.ArrayList;

public class IntentPalvelu extends IntentService {

    public final static String MY_SERVICE_NAME = "IntentPalvelu";
    public final static String EXTRA_KEY_OUT = "data_out_key";
    public final static String EXTRA_KEY_TIME = "data_out_time";
    public final static String EXTRA_KEY_ON_OFF = "data_out_on_off";
    public final static String ACTION_GET_ALL = "action_get_all";
    public final static String ACTION_SAVE_TIMESTAMP = "action_save_timestamp";
    ItemDatabase itemDatabase;
    ItemDao itemDao;

    public IntentPalvelu() {
        super(MY_SERVICE_NAME);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        itemDatabase = ItemDatabase.getDatabaseInstance(getApplicationContext());
        itemDao = itemDatabase.itemDao();

        if (intent.getAction().equals(ACTION_GET_ALL)) {

            ArrayList<ItemEntity> itemEntities = (ArrayList<ItemEntity>) itemDao.getAllItems();

            // Send all timestamps to main activity
            Intent dataIntent = new Intent();
            dataIntent.setAction(MainActivity.ACTION_GET_ALL_DATA);
            dataIntent.putExtra(EXTRA_KEY_OUT, itemEntities);
            sendBroadcast(dataIntent);
        }
        else if (intent.getAction().equals(ACTION_SAVE_TIMESTAMP)) {

            // Save one timestamp to database.
            ItemEntity itemEntity = new ItemEntity();
            String action = intent.getStringExtra(EXTRA_KEY_ON_OFF);
            String time = intent.getStringExtra(EXTRA_KEY_TIME);
            itemEntity.action = action;
            itemEntity.time = time;

            itemDao.insertItem(itemEntity);

            // Send update to main activity.
            Intent dataIntent = new Intent();
            dataIntent.setAction(MainActivity.ACTION_UPDATE_LISTVIEW);
            dataIntent.putExtra(EXTRA_KEY_OUT, itemEntity);
            sendBroadcast(dataIntent);
        }

    }
}
