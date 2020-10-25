package com.android.advancesettings.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import androidx.annotation.Nullable;

import com.android.advancesettings.R;
import com.android.advancesettings.entity.ShellResult;
import com.android.advancesettings.utils.ShellUtils;

public abstract class BaseFragment extends PreferenceFragment {
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case 100:
                    ShellResult shellResult = (ShellResult) msg.obj;
                    int result = shellResult.getResult();
                    if (result == 0) {
                        showDialog(shellResult.getSuccessMsg());
                    }else {
                        showDialog(shellResult.getErrorMsg());
                    }
                    break;
            }
            return false;
        }
    });
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(loadXml());
        init();
    }

    protected abstract void init();

    protected abstract int loadXml();

    @SuppressLint("StaticFieldLeak")
    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        String key = preference.getKey();
        if (key != null && key.startsWith("sh")) {
            final String command = key.substring(3);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ShellResult shellResult = ShellUtils.execSh(command);
                    Message message = mHandler.obtainMessage();
                    message.obj = shellResult;
                    message.what = 100;
                    mHandler.sendMessage(message);
                }
            }).start();
        }
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }

    private void showDialog(String result) {
        new AlertDialog.Builder(getActivity()).setTitle(R.string.sh_result)
                .setMessage(result)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }
}
