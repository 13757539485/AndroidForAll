package com.android.advancesettings;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.android.advancesettings.utils.ShellUtils;

public class SettingActivity extends BaseActivity implements miui.view.ViewPager.OnPageChangeListener {

    @Override
    public void onBaseOnCreate(Bundle bundle) {
        miui.app.ActionBar actionBar = getActionBar();
//        actionBar.setFragmentViewPagerMode(this, getFragmentManager());
//        actionBar.addFragmentTab("1", actionBar.newTab().setText("状态栏"), SystemUIFragment.class, null, false);
//        actionBar.addFragmentTab("2", actionBar.newTab().setText(R.string.tab_system), SystemFragment.class, null, false);
//        actionBar.addFragmentTab("3", actionBar.newTab().setText(R.string.tab_other), OtherFragment.class, null, false);
//        actionBar.addFragmentTab("4", actionBar.newTab().setText("DIY功能"), CustomFragment.class, null, false);
        setContentView(R.layout.settings);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.system, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.reboot:
                ShellUtils.reboot();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {

    }
}
