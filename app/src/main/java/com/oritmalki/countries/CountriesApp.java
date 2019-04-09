package com.oritmalki.countries;

import android.app.Application;

import com.oritmalki.countries.dagger.AppComponent;
import com.oritmalki.countries.dagger.DaggerAppComponent;
import com.oritmalki.countries.dagger.DataModule;
import com.oritmalki.countries.dagger.NetModule;

public class CountriesApp extends Application {

    private static AppComponent mAppComponent;
    public static AppComponent getComponent() {
        return mAppComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = buildAppComponent();
    }

    protected AppComponent buildAppComponent() {
        return DaggerAppComponent.builder()
                .netModule(new NetModule())
                .dataModule(new DataModule())
                .build();
    }
}
