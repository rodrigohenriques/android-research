package com.m4u.mockserver;

import android.app.Application;

/**
 * Created by admin on 30/09/15.
 */
public class MockApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        MockServer mockServer = new MockServer();
    }
}
