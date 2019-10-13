package com.android.advancesettings;

import android.os.Bundle;

import miui.preference.PreferenceActivity;

public abstract class BaseActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle bundle) {
        setTheme(miui.R.style.Theme_Light_Settings);
        super.onCreate(bundle);
        onBaseOnCreate(bundle);
    }

    public abstract void onBaseOnCreate(Bundle bundle);
}
