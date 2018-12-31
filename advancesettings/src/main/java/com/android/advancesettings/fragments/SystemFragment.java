package com.android.advancesettings.fragments;

import android.preference.EditTextPreference;
import android.preference.Preference;
import android.widget.Toast;

import com.android.advancesettings.R;
import com.android.advancesettings.utils.ShellUtils;

import java.io.IOException;

public class SystemFragment extends BaseFragment implements Preference.OnPreferenceChangeListener {

    private String mCurrentDPI;
    private EditTextPreference mDensity;

    @Override
    protected void init() {
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
            ShellUtils.execCommand("mount -o rw,remount /system");
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
}
