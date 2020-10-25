package com.android.advancesettings.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceScreen;
import android.widget.Toast;

import com.android.advancesettings.AppActivity;
import com.android.advancesettings.QtActivity;
import com.android.advancesettings.R;
import com.android.advancesettings.WifiActivity;
import com.android.advancesettings.constant.Constant;
import com.android.advancesettings.utils.SPManager;
import com.android.advancesettings.utils.ShellUtils;

import java.io.File;
import java.io.IOException;

public class SystemFragment extends BaseFragment implements Preference.OnPreferenceChangeListener {

    private String mCurrentDPI;
    private EditTextPreference mDensity;

    @Override
    protected void init() {
        ShellUtils.mountSystem();
        ShellUtils.mountData();
        Dpi();
    }

    private void Dpi() {
        this.mDensity = (EditTextPreference) findPreference("lcd_density_prefs_key");
        try {
            this.mCurrentDPI = ShellUtils.catCommand("cat /system/build.prop | busybox grep \"ro.sf.lcd_density\" | busybox sed 's/ro.sf.lcd_density=//g'\n");
            this.mDensity.setPersistent(false);
            this.mDensity.setText(this.mCurrentDPI);
            this.mDensity.setDefaultValue(this.mCurrentDPI);
            this.mDensity.toString();
            this.mDensity.setSummary(getString(R.string.dpi_value, mCurrentDPI));
            this.mDensity.setOnPreferenceChangeListener(this);
        } catch (IOException localIOException) {
            while (true) {
                localIOException.printStackTrace();
            }
        }
    }

    @Override
    protected int loadXml() {
        return R.xml.preference_system;
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String str = (String) newValue;
        if (str.equals(mCurrentDPI)) {
            mDensity.setSummary(getString(R.string.dpi_value, mCurrentDPI));
            return true;
        }
        try {
            int i = Integer.parseInt(str);
            if (i < 100 || i > 540) {
                Toast.makeText(getActivity(), R.string.input_valid_number, Toast.LENGTH_LONG).show();
                return false;
            }
            ShellUtils.mountSystem();
            try {
                Thread.sleep(250);
                ShellUtils.catCommand(String.format("busybox sed -i 's/ro.sf.lcd_density=%s/ro.sf.lcd_density=%s/g' /system/build.prop\n", new Object[]{mCurrentDPI, str}));
                if (ShellUtils.catCommand("cat /system/build.prop | busybox grep \"ro.sf.lcd_density\"\n").contains(str)) {
                    mCurrentDPI = str;
                    Toast.makeText(getActivity(), R.string.modify_successful_after_reboot, Toast.LENGTH_LONG).show();
                }
                mDensity.setSummary(getString(R.string.dpi_value, str));
                return true;
            } catch (IOException | InterruptedException localIOException) {
                localIOException.printStackTrace();
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return false;
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        String key = preference.getKey();
        switch (key) {
            case "wk":
                showDialog(new String[]{
                        getString(R.string.delete),
                        getString(R.string.recovery),
                        getString(R.string.close_dialog)}, Constant.WK, key);
                break;
            case "host":
                showDialog(new String[]{
                        getString(R.string.no_intercept),
                        getString(R.string.only_remove_Ad),
                        getString(R.string.force_intercept),
                        getString(R.string.close_dialog)}, Constant.HOST, key);
                break;
            case "ifw":
                showDialog(new String[]{
                        getString(R.string.enable),
                        getString(R.string.disable),
                        getString(R.string.close_dialog)}, Constant.IFW, key);
                break;
            case "pptd":
                Intent intent = new Intent();
                intent.setClassName(Constant.PPTD_PACKAGE_NAME, Constant.PPTD_LAUNCHER);
                startActivity(intent);
                break;
            case "qtsz":
                startActivity(new Intent(getActivity(), QtActivity.class));
                break;
            case "wifi":
                startActivity(new Intent(getActivity(), WifiActivity.class));
                break;
            case "app":
                startActivity(new Intent(getActivity(), AppActivity.class));
                break;
        }
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }

    private void showDialog(final String[] items, final int position
            , final String key) {
        if (!isAdded()) return;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setSingleChoiceItems(items, SPManager.getInstance(getActivity()).getInt(key), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which != items.length - 1) {
                    SPManager.getInstance(getActivity()).putInt(key, which);
                }
                if (which == items.length - 1) {
                    dialog.dismiss();
                    return;
                }
                switch (position) {
                    case Constant.WK:
                        dealWK(dialog, which);
                        break;
                    case Constant.HOST:
                        dealHost(dialog, which);
                        break;
                    case Constant.IFW:
                        dealIFW(dialog, which);
                        break;
                }
            }
        });
        builder.setCancelable(false).create().show();
    }

    private void dealWK(DialogInterface dialog, int which) {
        switch (which) {
            case 0:
                //删除温控
//                File wkDir = new File("/system/etc/wk");
//                if (!wkDir.exists()) {
//                    ShellUtils.execCommand("mkdir /system/etc/wk");
//                }
//                File file = new File("/system/etc/wk/thermal-engine.conf");
//                if (!file.exists()) {
//                    ShellUtils.execCommand("for list in `find /system/vendor/etc/ -name 'thermal*.conf'`;do cp $list /system/etc/wk \ndone");
//                }
                File file = new File("/system/vendor/etc/thermal-engine.conf");
                if (file.exists()) {
                    ShellUtils.execCommand("for list in `find /system/vendor/etc/ -name 'thermal*.conf'`;do rm -rf $list \ndone");
                }
                break;
            case 1:
                //恢复温控
                file = new File("/system/vendor/etc/thermal-engine.conf");
                if (!file.exists()) {
                    ShellUtils.execCommand("for list in `find /system/etc/wk/ -name 'thermal*.conf'`;do cp $list /system/vendor/etc/\ndone");
                }
                break;
        }
        dialog.dismiss();
        showResultDialog();
    }

    private void dealHost(DialogInterface dialog, int which) {
        switch (which) {
            case 0:
                ShellUtils.execCommand("sed -i '3c #model-C' /system/etc/hosts\nsed -i '1,$s/#//g' /system/etc/hosts\nsed -i '3,$s/^/#&/g' /system/etc/hosts");
                break;
            case 1:
                ShellUtils.execCommand("sed -i '3c #model-A' /system/etc/hosts\nsed -i '200,$s/#//g' /system/etc/hosts");
                break;
            case 2:
                ShellUtils.execCommand("sed -i '3c #model-B' /system/etc/hosts\nsed -i '4,$s/#//g' /system/etc/hosts");
                break;
        }
        dialog.dismiss();
        showResultDialog();
    }

    private void dealIFW(DialogInterface dialog, int which) {
        switch (which) {
            case 0:
                ShellUtils.execCommand(new String[]{"cp /system/etc/ifw.xml /data/system/ifw/ifw.xml", "chmod -R 0644 /data/system/ifw/ifw.xml"});
                break;
            case 1:
                ShellUtils.execCommand(new String[]{"rm -rf /data/system/ifw/ifw.xml"});
                break;
        }
        dialog.dismiss();
        showResultDialog();
    }

    private void showResultDialog() {
        if (!isAdded()) return;
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.tip)
                .setMessage(getString(R.string.reboot_message))
                .setPositiveButton(R.string.reboot_now, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ShellUtils.reboot();
                    }
                })
                .setNegativeButton(R.string.not_reboot, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        dialog.setCancelable(false);
        dialog.show();
    }
}
