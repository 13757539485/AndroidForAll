package com.android.advancesettings.entity;

public class ShellResult {
    public String errorMsg;
    public String successMsg;

    @Override
    public String toString() {
        return "ShellResult{" +
                ", errorMsg='" + errorMsg + '\'' +
                ", successMsg='" + successMsg + '\'' +
                '}';
    }
}
