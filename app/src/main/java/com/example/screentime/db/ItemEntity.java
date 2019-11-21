package com.example.screentime.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class ItemEntity implements Serializable {

    @PrimaryKey (autoGenerate = true)
    public int id;
    public String time;
    public String action;
}
