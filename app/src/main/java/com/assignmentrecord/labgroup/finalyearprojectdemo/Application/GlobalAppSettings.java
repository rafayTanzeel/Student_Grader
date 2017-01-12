package com.assignmentrecord.labgroup.finalyearprojectdemo.Application;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class GlobalAppSettings extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RealmConfiguration RConfig = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(RConfig);
    }

}
