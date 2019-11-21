package com.example.screentime.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = ItemEntity.class, version = 1, exportSchema = false)
public abstract class ItemDatabase extends RoomDatabase {
    private static volatile ItemDatabase INSTANCE;
    private static final String  DATABASE_NAME = "database";

    public static ItemDatabase getDatabaseInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (ItemDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ItemDatabase.class, DATABASE_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    public abstract ItemDao itemDao();
}
