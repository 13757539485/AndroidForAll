package com.android.advancesettings.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.support.annotation.Nullable;

import com.android.advancesettings.utils.ShellUtils;

public abstract class BaseFragment extends PreferenceFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(loadXml());
        init();
    }

    protected abstract void init();

    protected abstract int loadXml();

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        String key = preference.getKey();
        if (key != null && key.startsWith("sh")) {
            String commod = key.substring(3);
            ShellUtils.CommandResult commandResult = ShellUtils.execCommand(commod + " &", false);
            int result = commandResult.result;
            if (result == 0) {
                showDialog("应用成功");
            } else {
                showDialog(commandResult.errorMsg);
            }
        }
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }

    private void showDialog(String result) {
        new AlertDialog.Builder(getActivity()).setTitle("脚本执行")
                .setMessage(result)
                .setPositiveButton("确定", null)
                .show();
    }
}
