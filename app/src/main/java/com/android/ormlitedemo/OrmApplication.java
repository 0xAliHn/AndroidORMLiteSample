package com.android.ormlitedemo;

import android.app.Application;


public class OrmApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DatabaseHelper.initOrmLite(this);
    }
}
