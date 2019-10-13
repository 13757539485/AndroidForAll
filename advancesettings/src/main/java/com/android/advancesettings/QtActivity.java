package com.android.advancesettings;

import android.os.Bundle;

public class QtActivity extends BaseActivity {

    @Override
    public void onBaseOnCreate(Bundle bundle) {
        addPreferencesFromResource(R.xml.qt);
    }
}
