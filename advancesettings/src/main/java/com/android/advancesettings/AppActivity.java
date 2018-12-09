package com.android.advancesettings;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.android.advancesettings.adapter.AppAdapter;
import com.android.advancesettings.entity.AppBean;
import com.android.advancesettings.utils.ShellUtils;

import java.util.ArrayList;
import java.util.List;

import miui.app.ProgressDialog;
import miui.preference.PreferenceActivity;

public class AppActivity extends PreferenceActivity {

    private AppAdapter mAppAdapter;
    private PopupWindow mWindow;
    private ArrayList<AppBean> mAllApps = new ArrayList<>();
    private ArrayList<AppBean> mSystemApps = new ArrayList<>();
    private ArrayList<AppBean> mUserApps = new ArrayList<>();
    private int mCurrentItemPosition = -1;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(miui.R.style.Theme_Light_Settings);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        /*linearLayout = (LinearLayout) findViewById(R.id.linear1);
        Button button = (Button) findViewById(R.id.start_delete);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DeleteApk().execute();
            }
        });*/
        initPopupWindow();
        ImageView menuView = findViewById(R.id.menu_button);
        menuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 显示PopupWindow，其中：
                // 第一个参数是PopupWindow的锚点，第二和第三个参数分别是PopupWindow相对锚点的x、y偏移
                mWindow.showAsDropDown(v, 0, 0);
                // 或者也可以调用此方法显示PopupWindow，其中：
                // 第一个参数是PopupWindow的父View，第二个参数是PopupWindow相对父View的位置，
                // 第三和第四个参数分别是PopupWindow相对父View的x、y偏移
                // window.showAtLocation(parent, gravity, x, y);
            }
        });
        mAppAdapter = new AppAdapter(this);
        getListView().setAdapter(mAppAdapter);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AppBean item = mAppAdapter.getItem(position);
                if (mAppAdapter.isMultipleMode()) {
                    mAppAdapter.addSelectItem(item);
                } else {
                    mCurrentItemPosition = position;
                    showDialog();
                }
            }
        });
        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mAppAdapter.setMultipleMode(true);
                return true;
            }
        });

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                showWaitDialog("获取应用列表");
            }

            @Override
            protected Void doInBackground(Void... voids) {
                initInstalledApps();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                mProgressDialog.dismiss();
                mAppAdapter.addAll(mAllApps);
            }
        }.execute();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
                if (mAppAdapter.isMultipleMode()) {
                    mAppAdapter.setMultipleMode(false);
                    return true;
                }
            }
        }
        return super.dispatchKeyEvent(event);
    }

    private void initPopupWindow() {
        // 用于PopupWindow的View
        View contentView = LayoutInflater.from(this).inflate(R.layout.popuwindow_menu, null, false);
        // 创建PopupWindow对象，其中：
        // 第一个参数是用于PopupWindow中的View，第二个参数是PopupWindow的宽度，
        // 第三个参数是PopupWindow的高度，第四个参数指定PopupWindow能否获得焦点
        mWindow = new PopupWindow(contentView, 500, 600, true);
        // 设置PopupWindow的背景
        mWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // 设置PopupWindow是否能响应外部点击事件
        mWindow.setOutsideTouchable(true);
        // 设置PopupWindow是否能响应点击事件
        mWindow.setTouchable(true);
    }

    private void initInstalledApps() {
        mAllApps.clear();
        mSystemApps.clear();
        mUserApps.clear();
        PackageManager packageManager = getPackageManager();
        List<PackageInfo> allPackageInfos = packageManager.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
        for (PackageInfo packageInfo : allPackageInfos) {
            AppBean item = new AppBean();
            Intent launchIntent = packageManager.getLaunchIntentForPackage(packageInfo.packageName);
            item.setLauncherIntent(launchIntent);
            Drawable loadIcon = packageInfo.applicationInfo.loadIcon(packageManager);
            item.setIcon(loadIcon);
            CharSequence label = packageInfo.applicationInfo.loadLabel(packageManager);
            item.setLabel(label.toString());
            item.setVersion(packageInfo.versionName);
            item.setPackage(packageInfo.packageName);
            item.setApkPath(packageInfo.applicationInfo.sourceDir);
            boolean isSystemApp = (packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 1;
            item.setSystem(isSystemApp);
            item.setChecked(false);
            if (isSystemApp) {
                mSystemApps.add(item);
            } else {
                mUserApps.add(item);
            }
            mAllApps.add(item);
        }
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String[] items = new String[]{"打开","查看","冻结","卸载"};
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final AppBean item = mAppAdapter.getItem(mCurrentItemPosition);
                switch (which) {
                    case 0:
                        Intent launcherIntent = item.getLauncherIntent();
                        if (launcherIntent != null) {
                            startActivity(launcherIntent);
                        } else {
                            Toast.makeText(AppActivity.this, "此应用无法启动！", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 1:
                        Intent localIntent = new Intent();
                        localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                        localIntent.setData(Uri.fromParts("package", item.getAppPackage(), null));
                        startActivity(localIntent);
                        break;
                    case 2:
                        new AsyncTask<Void, Void, Void>() {
                            @Override
                            protected void onPreExecute() {
                                showWaitDialog("冻结");
                            }

                            @Override
                            protected Void doInBackground(Void... voids) {
                                ShellUtils.execCommand("pm disable " + item.getAppPackage());
                                return null;
                            }

                            @Override
                            protected void onPostExecute(Void aVoid) {
                                mProgressDialog.dismiss();
                                mAppAdapter.remove(item);
                                mAppAdapter.notifyDataSetChanged();
                            }
                        }.execute();
                        break;
                    case 3:
                        new DeleteApk().execute();
                        break;
                }
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private class DeleteApk extends AsyncTask {
        @Override
        protected void onPreExecute() {
            showWaitDialog("卸载应用");
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            if (mAppAdapter.isMultipleMode()) {

            } else {
                uninstall(mAppAdapter.getItem(mCurrentItemPosition));
            }
            return true;
        }

        @Override
        protected void onPostExecute(Object o) {
            mProgressDialog.dismiss();
            if (mAppAdapter.isMultipleMode()) {
                mAppAdapter.removeSelectItems();
            } else {
                mAppAdapter.remove(mAppAdapter.getItem(mCurrentItemPosition));
            }
        }
    }

    private void showWaitDialog(String title) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(AppActivity.this);
        }
        mProgressDialog.setTitle(title);
        mProgressDialog.setMessage("请稍后...");
        mProgressDialog.setCancelable(true);
        mProgressDialog.show();
    }

    private void uninstall(AppBean appBean) {
        boolean system = appBean.isSystem();
        if (system) {
            ShellUtils.execCommand("pm uninstall --user 0 " + appBean.getAppPackage());
            ShellUtils.execCommand("rm -rf " + appBean.getApkPath());
        } else {
            ShellUtils.execCommand("pm uninstall " + appBean.getAppPackage());
        }
    }
}
