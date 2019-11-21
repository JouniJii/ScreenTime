package com.example.screentime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.screentime.db.ItemEntity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private OmaReceiver omaReceiver;
    private DataReceiver dataReceiver;
    private Context context;

    final static String ACTION_GET_ALL_DATA = "action_data_receiver";
    final static String ACTION_UPDATE_LISTVIEW = "action_update_listview";
    final static String APP_STARTED_TEXT = "ScreenTime started";
    final static String APP_STOPPED_TEXT = "ScreenTime stopped";

    ArrayList<ItemEntity> items = new ArrayList<>();
    ArrayAdapter<ItemEntity> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (context == null) {
            context = getApplicationContext();
        }

        ListView listView = findViewById(R.id.listView);
        adapter = new ArrayAdapteri(this, R.layout.listview_item, items);
        listView.setAdapter(adapter);

        setupDataReceiver();
        setupOmaReceiver();

        // Get all timestamps and actions to listview.
        getAllTimes();

        // Save start time to database.
        omaReceiver.saveAction(context, APP_STARTED_TEXT);

    }

    private void getAllTimes() {
        Intent intent = new Intent();
        intent.setAction(IntentPalvelu.ACTION_GET_ALL);
        intent.setClass(context, IntentPalvelu.class);
        startService(intent);
    }

    private void setupOmaReceiver(){
        omaReceiver = new OmaReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        registerReceiver(omaReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Save stop time to database.
        omaReceiver.saveAction(context, APP_STOPPED_TEXT);
        unregisterReceiver(omaReceiver);
        unregisterReceiver(dataReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setupDataReceiver() {
        dataReceiver = new DataReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_GET_ALL_DATA);
        intentFilter.addAction(ACTION_UPDATE_LISTVIEW);
        registerReceiver(dataReceiver, intentFilter);
    }

    public class DataReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            // Received all data from IntentPalvelu
            if (intent.getAction().equals(ACTION_GET_ALL_DATA)) {

                items = (ArrayList<ItemEntity>) intent.getSerializableExtra(IntentPalvelu.EXTRA_KEY_OUT);
                if (items != null) {
                    adapter.addAll(items);
                    adapter.notifyDataSetChanged();
                }
            }
            // Got one timestamp item to the listview from IntentPalvelu.
            else if (intent.getAction().equals(ACTION_UPDATE_LISTVIEW)) {
                ItemEntity itemEntity = (ItemEntity) intent.getSerializableExtra(IntentPalvelu.EXTRA_KEY_OUT);

                adapter.insert(itemEntity, 0);
                adapter.notifyDataSetChanged();
            }
        }
    }
}
