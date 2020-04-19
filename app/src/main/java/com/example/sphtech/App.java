package com.example.sphtech;

import android.app.Application;

import com.example.sphtech.model.db.DbHelper;
import com.example.sphtech.model.http.RetrofitUtils;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        RetrofitUtils.init("https://data.gov.sg/");

        DbHelper.init(getApplicationContext());
    }

}
