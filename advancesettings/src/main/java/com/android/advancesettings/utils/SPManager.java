package com.android.advancesettings.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SPManager {
    private static volatile SPManager sSPManager;
    private volatile SharedPreferences mPreferences;

    private SPManager(Context context) {
        if (mPreferences == null)
            mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static SPManager getInstance(Context context) {
        if (sSPManager == null)
            sSPManager = new SPManager(context);
        return sSPManager;
    }

    public void putString(String key, String value) {
        mPreferences.edit().putString(key, value).apply();
    }

    public String getString(String key) {
        return mPreferences.getString(key, "");
    }

    public void putInt(String key, int value) {
        mPreferences.edit().putInt(key, value).apply();
    }

    public int getInt(String key) {
        return mPreferences.getInt(key, -1);
    }
}
