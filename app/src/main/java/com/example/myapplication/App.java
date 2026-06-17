package com.example.myapplication;

import android.app.Application;

import com.example.myapplication.ui.network.RetrofitClient;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RetrofitClient.init(this);
    }
}
