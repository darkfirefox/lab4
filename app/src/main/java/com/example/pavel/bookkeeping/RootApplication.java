package com.example.pavel.bookkeeping;

import android.app.Application;

public class RootApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        setTheme(R.style.AppTheme);
    }
}
