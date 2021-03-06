package com.android.advancesettings;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.advancesettings.adapter.WifiAdapter;
import com.android.advancesettings.constant.Constant;
import com.android.advancesettings.utils.ReadWifi;
import com.android.advancesettings.utils.ShellUtils;

import java.util.ArrayList;
import java.util.Map;

public class WifiActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private ListView mWifiList;
    private WifiAdapter mWifiAdapter;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case 100:
                    ArrayList<Map<String, String>> list = (ArrayList<Map<String, String>>) msg.obj;
                    mWifiAdapter.addAllItem(list);
                    break;
            }
            return false;
        }
    });
    private WifiRunnable mWifiRunnable;
    private ClipboardManager mClipboard;
    private int mCurrentItemPosition;

    @Override
    public void onBaseOnCreate(Bundle bundle) {
        miui.app.ActionBar actionBar = getActionBar();
        actionBar.setTitle(R.string.wifi_title);
        if (ShellUtils.mountData()) {
            setContentView(R.layout.activity_wifi);
            mClipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            mWifiList = findViewById(android.R.id.list);
            mWifiAdapter = new WifiAdapter(this);
            mWifiList.setAdapter(mWifiAdapter);
            mWifiList.setOnItemClickListener(this);
            String path;
            if (Build.VERSION.SDK_INT >= 26) {
                path = Constant.WIFI_CONFIG_PATH_NEW;
            } else {
                path = Constant.WIFI_CONFIG_PATH;
            }
            mWifiRunnable = new WifiRunnable(path);
            new Thread(mWifiRunnable).start();
        } else {
            Toast.makeText(this, R.string.unable_to_get_root, Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWifiRunnable != null) {
            mHandler.removeCallbacks(mWifiRunnable);
            mWifiRunnable = null;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mCurrentItemPosition = position;
        showDialog();
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String[] items = new String[]{getString(R.string.copy_username),
                getString(R.string.copy_pwd),
                getString(R.string.copy_username_and_pwd),
                getString(R.string.more_operation)};
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Map<String, String> item = mWifiAdapter.getItem(mCurrentItemPosition);
                switch (which) {
                    case 0:
                        copy(item.get("ssid"));
                        break;
                    case 1:
                        copy(item.get("psk"));
                        break;
                    case 2:
                        copy(getString(R.string.wifi_name, item.get("ssid")) + "\n"
                                + getString(R.string.wifi_pwd, item.get("psk")));
                        break;
                    case 3:
                        Intent intent = new Intent();
                        intent.setClassName("com.android.settings",
                                "com.android.settings.wifi.WifiSettings");
                        startActivity(intent);
                        break;
                }
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void copy(String text) {
        if (mClipboard != null) {
            ClipData clip = ClipData.newPlainText("simple text", text);
            mClipboard.setPrimaryClip(clip);
            Toast.makeText(this, R.string.already_copy, Toast.LENGTH_SHORT).show();
        }
    }

    private class WifiRunnable implements Runnable {
        private String mPath;

        public WifiRunnable(String path) {
            mPath = path;
        }

        @Override
        public void run() {
            try {
                ArrayList<Map<String, String>> list = new ReadWifi(mPath).getList(WifiActivity.this);
                Message message = mHandler.obtainMessage();
                message.obj = list;
                message.what = 100;
                mHandler.sendMessage(message);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }
}
