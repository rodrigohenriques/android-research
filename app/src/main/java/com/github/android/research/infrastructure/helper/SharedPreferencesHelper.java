package com.github.android.research.infrastructure.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesHelper {

    private static final String TOKEN_PREF = "token_pref";
    private static final String USERNAME_PREF = "username_pref";

    private static SharedPreferencesHelper instance = null;
    Context mContext;
    SharedPreferences sharedPreferences;

    public SharedPreferencesHelper(Context context) {
        this.mContext = context;

        String preferencesName = this.getPackageName();
        this.sharedPreferences = this.mContext.getSharedPreferences(
                preferencesName, Context.MODE_PRIVATE);
    }

    public static SharedPreferencesHelper getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferencesHelper(context);
        }

        return instance;
    }

    public String getString(String key, String defaultValue) {
        return this.sharedPreferences.getString(key, defaultValue);
    }

    public String getString(String key) {
        return this.getString(key, null);
    }

    public boolean setString(String key, String value) {
        Editor edit = this.sharedPreferences.edit();
        edit.putString(key, value);
        return edit.commit();
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return this.sharedPreferences.getBoolean(key, defaultValue);
    }

    public boolean setBoolean(String key, boolean value) {
        Editor edit = this.sharedPreferences.edit();
        edit.putBoolean(key, value);
        return edit.commit();
    }

    public String getPackageName() {
        return this.mContext.getApplicationContext().getPackageName();
    }

    public boolean setToken(String token) {
        return setString(TOKEN_PREF, token);
    }

    public String getToken() {
        return getString(TOKEN_PREF);
    }

    public boolean isLoggedIn() {
        return getToken() != null && getUsername() != null;
    }

    public boolean setUsername(String username) {
        return setString(USERNAME_PREF, username);
    }

    public String getUsername() {
        return getString(USERNAME_PREF);
    }
}