package com.example.wfrs06.hw5.models;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.wfrs06.hw5.dao.SettingsDao;
import com.example.wfrs06.hw5.entity.Settings;


@Database(entities = {Settings.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract SettingsDao settingsDao();
}
