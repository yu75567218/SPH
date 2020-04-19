package com.example.sphtech.model.db;

import android.content.Context;

import androidx.room.Room;


public class DbHelper {

    private static MyDatabase myDatabase;

    public static void init(Context context) {
        myDatabase = Room.databaseBuilder(context, MyDatabase.class, "sph")
                .fallbackToDestructiveMigration()
                .build();
    }

    public static MyDatabase getInstance() {
        return myDatabase;
    }
}
