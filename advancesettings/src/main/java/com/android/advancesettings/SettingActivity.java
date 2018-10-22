package com.android.advancesettings;

import android.os.Bundle;

import com.android.advancesettings.fragments.CustomFragment;
import com.android.advancesettings.fragments.SystemUIFragment;

import miui.preference.PreferenceActivity;

public class SettingActivity extends PreferenceActivity implements miui.view.ViewPager.OnPageChangeListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(miui.R.style.Theme_Light_Settings);
        super.onCreate(savedInstanceState);
        miui.app.ActionBar actionBar = getActionBar();
        actionBar.setFragmentViewPagerMode(this,getFragmentManager());
        actionBar.addFragmentTab("1",actionBar.newTab().setText("状态栏"),SystemUIFragment.class,null,false);
        actionBar.addFragmentTab("2",actionBar.newTab().setText("系统"),SystemUIFragment.class,null,false);
        actionBar.addFragmentTab("3",actionBar.newTab().setText("其他"),SystemUIFragment.class,null,false);
        actionBar.addFragmentTab("4",actionBar.newTab().setText("DIY功能"),CustomFragment.class,null,false);
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
