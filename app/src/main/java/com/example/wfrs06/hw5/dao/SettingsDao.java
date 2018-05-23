package com.example.wfrs06.hw5.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;


import com.example.wfrs06.hw5.entity.Settings;

import java.util.List;

@Dao
public interface SettingsDao {

    @Query("SELECT * FROM Settings")
    List<Settings> getAll();


    @Query("SELECT * FROM Settings WHERE id = :id")
    List<Settings> loadAllByIds(int id);
/*
    @Query("SELECT * FROM Settings WHERE first_name LIKE :firstName and last_name LIKE :lastName LIMIT 1")
    User findByName(String firstName, String lastName);

*/
    @Update
    void updateSettings(Settings... settings);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Settings... settings);

    @Delete
    void delete(Settings settings);

}