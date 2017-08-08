package com.example.luyan.smartmenu_process.Common;

import android.app.Application;
import android.content.Context;

import com.example.luyan.smartmenu_process.Utils.GreenDaoUtils.GreenDaoManager;


/**
 * Created by luyan on 10/19/16.
 */

public class MyApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        GreenDaoManager.getInstance();
    }

    public static Context getContext() {
        return mContext;
    }
}

