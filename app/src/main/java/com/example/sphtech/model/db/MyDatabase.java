package com.example.sphtech.model.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.sphtech.model.bean.BeanYear;

@Database(entities = {BeanYear.class}, version = 1)
public abstract class MyDatabase extends RoomDatabase {
    public abstract DataDao dataDao();
}
