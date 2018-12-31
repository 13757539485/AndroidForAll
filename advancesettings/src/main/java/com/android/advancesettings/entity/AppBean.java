//
// Decompiled by Jadx - 807ms
//
package com.android.advancesettings.entity;

import android.content.Intent;
import android.graphics.drawable.Drawable;

import java.util.Objects;

public class AppBean {
    private String apkPath;
    private boolean isDisable;
    private String appPackage;
    private Drawable icon;
    private String label;
    private boolean system;
    private String version;
    private Intent launcherIntent;

    public Intent getLauncherIntent() {
        return launcherIntent;
    }

    public void setLauncherIntent(Intent launcherIntent) {
        this.launcherIntent = launcherIntent;
    }

    public String getApkPath() {
        return this.apkPath;
    }

    public String getAppPackage() {
        return this.appPackage;
    }

    public Drawable getIcon() {
        return this.icon;
    }

    public String getLabel() {
        return this.label;
    }

    public String getTitle() {
        return this.version == null ? this.label : this.label + " " + this.version;
    }

    public String getVersion() {
        return this.version;
    }

    public boolean isDisable() {
        return this.isDisable;
    }

    public boolean isSystem() {
        return this.system;
    }

    public void setApkPath(String str) {
        this.apkPath = str;
    }

    public void setDisable(boolean z) {
        this.isDisable = z;
    }

    public void setPackage(String str) {
        this.appPackage = str;
    }

    public void setIcon(Drawable drawable) {
        this.icon = drawable;
    }

    public void setLabel(String str) {
        this.label = str;
    }

    public void setSystem(boolean z) {
        this.system = z;
    }

    public void setVersion(String str) {
        this.version = str;
    }

    @Override
    public boolean equals(Object o) {
        AppBean appBean = (AppBean) o;
        return Objects.equals(appPackage, appBean.appPackage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appPackage);
    }
}