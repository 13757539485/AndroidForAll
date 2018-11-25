//
// Decompiled by Jadx - 807ms
//
package com.android.advancesettings.entity;

import android.graphics.drawable.Drawable;

public class AppBean {
    private String apkPath;
    private boolean checked;
    private String description;
    private Drawable icon;
    private String label;
    private long lastModified;
    private long size;
    private boolean system;
    private String version;

    public String getApkPath() {
        return this.apkPath;
    }

    public String getDescription() {
        return this.description;
    }

    public Drawable getIcon() {
        return this.icon;
    }

    public String getLabel() {
        return this.label;
    }

    public long getLastModified() {
        return this.lastModified;
    }

    public long getSize() {
        return this.size;
    }

    public String getTitle() {
        return this.version == null ? this.label : this.label + " " + this.version;
    }

    public String getVersion() {
        return this.version;
    }

    public boolean isChecked() {
        return this.checked;
    }

    public boolean isSystem() {
        return this.system;
    }

    public void setApkPath(String str) {
        this.apkPath = str;
    }

    public void setChecked(boolean z) {
        this.checked = z;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public void setIcon(Drawable drawable) {
        this.icon = drawable;
    }

    public void setLabel(String str) {
        this.label = str;
    }

    public void setLastModified(long j) {
        this.lastModified = j;
    }

    public void setSize(long j) {
        this.size = j;
    }

    public void setSystem(boolean z) {
        this.system = z;
    }

    public void setVersion(String str) {
        this.version = str;
    }
}