package com.example.screentime;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.screentime.db.ItemEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class ArrayAdapteri extends ArrayAdapter<ItemEntity> {

    private LayoutInflater inflater;
    private ArrayList<ItemEntity> itemEntities;
    private int item_layout;

    public ArrayAdapteri(@NonNull Context context, int resource, @NonNull ArrayList<ItemEntity> objects) {
        super(context, resource, objects);

        this.item_layout = resource;
        this.itemEntities = objects;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        super.getView(position, convertView, parent);

        ItemEntity itemEntity = itemEntities.get(position);

        if (convertView == null) {
            convertView = inflater.inflate(item_layout, parent, false);
        }

        TextView textView1 = convertView.findViewById(R.id.textView1);
        TextView textView2 = convertView.findViewById(R.id.textView2);

        textView1.setText(itemEntity.action);
        textView2.setText(itemEntity.time);

        return convertView;
    }
}
