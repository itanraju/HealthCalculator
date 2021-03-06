package com.example.healthcalculator.Util;

import android.app.Application;
import android.content.Context;

import com.example.healthcalculator.OpenAds.AppOpenManager;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MyApplication extends Application {
    private static MyApplication mInstance;
    private static Context context;
    AppOpenManager appOpenManager;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        context = getApplicationContext();

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        appOpenManager = new AppOpenManager(this);
    }
}
