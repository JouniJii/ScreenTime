package com.example.screentime.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ItemDao {

    @Insert
    void insertItem (ItemEntity itemEntity);

    @Query("SELECT * FROM ItemEntity ORDER BY id DESC")
    List<ItemEntity> getAllItems();
}
