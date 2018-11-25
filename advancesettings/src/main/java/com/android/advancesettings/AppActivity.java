package com.android.advancesettings;

import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;

import com.android.advancesettings.entity.AppBean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import miui.preference.PreferenceActivity;

public class AppActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(miui.R.style.Theme_Light_Settings);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
    }

    private List<AppBean> getInstalledApps() {
        List<AppBean> arrayList = new ArrayList();
        List installedPackages = getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < installedPackages.size(); i += 1) {
            PackageInfo packageInfo = (PackageInfo) installedPackages.get(i);
            Object obj = (packageInfo.applicationInfo.flags & 1) > 0 ? 1 : null;
            String displayOption = !false ? "REGULAR_APPS" : "";
            if ((!"REGULAR_APPS".equals(displayOption) || obj == null) && !("SYSTEM_APPS".equals(displayOption) && obj == null)) {
                Drawable loadIcon = packageInfo.applicationInfo.loadIcon(getPackageManager());
                String charSequence = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();
                String str = packageInfo.applicationInfo.sourceDir;
                Log.d("getInstalledApps()", "source dir: " + packageInfo.applicationInfo.sourceDir);
                File file = new File(str);
                AppBean item = new AppBean();
                item.setIcon(loadIcon);
                item.setLabel(charSequence);
                item.setVersion(packageInfo.versionName);
                item.setDescription(packageInfo.packageName);
                item.setApkPath(str);
                item.setLastModified(file.lastModified());
                item.setSize(file.length());
                item.setSystem(obj != null);
                item.setChecked(false);
                arrayList.add(item);
            }
        }
        return arrayList;
    }
}
