package com.android.advancesettings;

import android.app.Application;
import android.widget.Toast;

import com.android.advancesettings.utils.ShellUtils;

public class SettingApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        boolean haveRoot = ShellUtils.isRoot();
        if (!haveRoot) {
            Toast.makeText(this, "未获取su权限", Toast.LENGTH_SHORT).show();
        }
    }
}
