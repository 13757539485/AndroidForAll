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
import android.os.Environment;
import android.util.Log;
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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import miui.app.ProgressDialog;
import miui.preference.PreferenceActivity;

public class AppActivity extends PreferenceActivity implements View.OnClickListener {

    private AppAdapter mAppAdapter;
    private PopupWindow mWindow;
    private ArrayList<AppBean> mAllApps = new ArrayList<>();
    private ArrayList<AppBean> mSystemApps = new ArrayList<>();
    private ArrayList<AppBean> mUserApps = new ArrayList<>();
    private ArrayList<AppBean> mDisableApps = new ArrayList<>();
    private int mCurrentItemPosition = -1;
    ProgressDialog mProgressDialog;
    private ImageView mMenuView;
    private ImageView mRefrigeratorView;
    private ImageView mDeleteView;
    private static final int ALL_DATA = 1;
    private static final int SYSTEM_DATA = 2;
    private static final int USER_DATA = 3;
    private static final int DISABLE_DATA = 4;
    private int mCurrentData = 1;
    private static final String BACK_PATH = Environment.getExternalStorageDirectory().getPath() + "/高级设置备份目录";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(miui.R.style.Theme_Light_Settings);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        setTitle("应用列表");
        initPopupWindow();
        mMenuView = findViewById(R.id.menu_button);
        mRefrigeratorView = findViewById(R.id.refrigerator);
        mDeleteView = findViewById(R.id.delete);
        //TextView emptyView = findViewById(android.R.id.empty);
        mMenuView.setOnClickListener(this);
        mRefrigeratorView.setOnClickListener(this);
        mDeleteView.setOnClickListener(this);
        mAppAdapter = new AppAdapter(this);
        getListView().setAdapter(mAppAdapter);
        //getListView().setEmptyView(emptyView);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AppBean item = mAppAdapter.getItem(position);
                if (mAppAdapter.isMultipleMode()) {
                    mAppAdapter.addSelectItem(item);
                } else {
                    mCurrentItemPosition = position;
                    showListDialog(mCurrentData == DISABLE_DATA);
                }
            }
        });
        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (!mAppAdapter.isMultipleMode()) {
                    mAppAdapter.setMultipleMode(true);
                    checkMultipleMode(false, false, true);
                }
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
                mAppAdapter.addAll(mAllApps, false);
            }
        }.execute();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
                if (mCurrentData == DISABLE_DATA) {
                    mCurrentData = ALL_DATA;
                    mAppAdapter.addAll(mAllApps, true);
                    checkMultipleMode(true, true, false);
                    if (mAppAdapter.isMultipleMode()) {
                        mAppAdapter.setMultipleMode(false);
                    }
                    return true;
                } else {
                    if (mAppAdapter.isMultipleMode()) {
                        mAppAdapter.setMultipleMode(false);
                        checkMultipleMode(true, true, false);
                        return true;
                    }
                }
            }
        }
        return super.dispatchKeyEvent(event);
    }

    private void checkMultipleMode(boolean menu, boolean ref, boolean del) {
        mMenuView.setVisibility(menu ? View.VISIBLE : View.GONE);
        mRefrigeratorView.setVisibility(ref ? View.VISIBLE : View.GONE);
        mDeleteView.setVisibility(del ? View.VISIBLE : View.GONE);
    }

    private void initPopupWindow() {
        // 用于PopupWindow的View
        View contentView = LayoutInflater.from(this).inflate(R.layout.popuwindow_menu, null, false);
        View allApp = contentView.findViewById(R.id.all_app_layout);
        View systemApp = contentView.findViewById(R.id.system_app_layout);
        View userApp = contentView.findViewById(R.id.user_app_layout);
        allApp.setOnClickListener(this);
        systemApp.setOnClickListener(this);
        userApp.setOnClickListener(this);
        // 创建PopupWindow对象，其中：
        // 第一个参数是用于PopupWindow中的View，第二个参数是PopupWindow的宽度，
        // 第三个参数是PopupWindow的高度，第四个参数指定PopupWindow能否获得焦点
        mWindow = new PopupWindow(contentView, 500, 500, true);
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
            boolean enabled = packageInfo.applicationInfo.enabled;
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
            item.setDisable(!enabled);
            if (!enabled) {
                mDisableApps.add(item);
            } else {
                if (isSystemApp) {
                    mSystemApps.add(item);
                } else {
                    mUserApps.add(item);
                }
                mAllApps.add(item);
            }
        }
    }

    private void showListDialog(final boolean isRecovery) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String[] items = new String[]{"打开", "查看", isRecovery ? "解冻" : "冻结", "卸载", "提取"};
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
                                showWaitDialog(isRecovery ? "解冻" : "冻结");
                            }

                            @Override
                            protected Void doInBackground(Void... voids) {
                                if (isRecovery) {
                                    ShellUtils.execCommand("pm enable " + item.getAppPackage());
                                } else {
                                    ShellUtils.execCommand("pm disable " + item.getAppPackage());
                                }
                                return null;
                            }

                            @Override
                            protected void onPostExecute(Void aVoid) {
                                mProgressDialog.dismiss();
                                item.setDisable(!isRecovery);
                                if (isRecovery) {
                                    if (item.isSystem()) {
                                        mSystemApps.add(item);
                                    } else {
                                        mUserApps.add(item);
                                    }
                                    mDisableApps.remove(item);
                                    mAllApps.add(item);
                                } else {
                                    if (item.isSystem()) {
                                        mSystemApps.remove(item);
                                    } else {
                                        mUserApps.remove(item);
                                    }
                                    mAllApps.remove(item);
                                    mDisableApps.add(item);
                                }
                                mAppAdapter.remove(item);
                                mAppAdapter.notifyDataSetChanged();
                            }
                        }.execute();
                        break;
                    case 3:
                        if (item.isSystem()) {
                            showWarnDialog("这是系统应用，此操作不可逆，确认卸载吗？可能会导致系统异常");
                        }
                        break;
                    case 4:
                        saveFile(item);
                        break;
                }
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void saveFile(final AppBean appBean) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                showWaitDialog("提取应用");
            }

            @Override
            protected Void doInBackground(Void... voids) {
                File file = new File(BACK_PATH);
                if (!file.exists()) {
                    file.mkdir();
                }
                String apkPath = appBean.getApkPath();
                String name = appBean.getTitle().replaceAll(" ","\\\\ ");
                ShellUtils.execCommand("cp " + apkPath + " " + BACK_PATH + File.separator + name + ".apk");
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                mProgressDialog.dismiss();
                showResultDialog(appBean);
            }
        }.execute();
    }

    private void showWarnDialog(String msg) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("危险提醒")
                .setMessage(msg)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new DeleteApk().execute();
                    }
                }).setNegativeButton("取消", null)
                .create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void showResultDialog(AppBean appBean) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("提取结果")
                .setMessage("已提取至"+ BACK_PATH +
                        File.separator + appBean.getTitle() + ".apk")
                .setPositiveButton("确定", null)
                .setNegativeButton("取消", null)
                .create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.menu_button:
                // 显示PopupWindow，其中：
                // 第一个参数是PopupWindow的锚点，第二和第三个参数分别是PopupWindow相对锚点的x、y偏移
                mWindow.showAsDropDown(v, 0, 0);
                // 或者也可以调用此方法显示PopupWindow，其中：
                // 第一个参数是PopupWindow的父View，第二个参数是PopupWindow相对父View的位置，
                // 第三和第四个参数分别是PopupWindow相对父View的x、y偏移
                // window.showAtLocation(parent, gravity, x, y);
                break;
            case R.id.all_app_layout:
                if (mCurrentData != ALL_DATA) {
                    mCurrentData = ALL_DATA;
                    mAppAdapter.addAll(mAllApps, true);
                }
                break;
            case R.id.system_app_layout:
                if (mCurrentData != SYSTEM_DATA) {
                    mCurrentData = SYSTEM_DATA;
                    mAppAdapter.addAll(mSystemApps, true);
                }
                break;
            case R.id.user_app_layout:
                if (mCurrentData != USER_DATA) {
                    mCurrentData = USER_DATA;
                    mAppAdapter.addAll(mUserApps, true);
                }
                break;
            case R.id.refrigerator:
                if (mCurrentData != DISABLE_DATA) {
                    checkMultipleMode(false, true, false);
                    mCurrentData = DISABLE_DATA;
                    mAppAdapter.addAll(mDisableApps, true);
                }
                break;
            case R.id.delete:
                if (mAppAdapter.getSelectItems().size() <= 0) {
                    Toast.makeText(this, "未选择卸载应用！", Toast.LENGTH_SHORT).show();
                    return;
                }
                showWarnDialog("确定一键卸载？");
                break;
        }
    }

    private class DeleteApk extends AsyncTask {
        @Override
        protected void onPreExecute() {
            showWaitDialog("卸载应用");
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            if (mAppAdapter.isMultipleMode()) {
                ArrayList<AppBean> selectItems = mAppAdapter.getSelectItems();
                for (AppBean app : selectItems) {
                    uninstall(app);
                }
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
                AppBean item = mAppAdapter.getItem(mCurrentItemPosition);
                boolean disable = item.isDisable();
                if (disable) {
                    mDisableApps.remove(item);
                } else {
                    if (item.isSystem()) {
                        mSystemApps.remove(item);
                    } else {
                        mUserApps.remove(item);
                    }
                    mAllApps.remove(item);
                }
                mAppAdapter.remove(item);
            }
        }
    }

    private void showWaitDialog(String title) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(AppActivity.this);
        }
        mProgressDialog.setTitle(title);
        mProgressDialog.setMessage("请稍后...");
        mProgressDialog.setCancelable(false);
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
