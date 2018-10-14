package com.android.advancesettings.adapter;

import android.app.FragmentManager;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final ArrayList<String> mArrayListTitle;
    private final ArrayList<PreferenceFragment> mFragmentList = new ArrayList<>();
    public ViewPagerAdapter(FragmentManager fm, ArrayList<String> titles) {
        super(fm);
        mArrayListTitle = titles;
    }

    @Override
    public PreferenceFragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(PreferenceFragment fragment) {
        mFragmentList.add(fragment);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mArrayListTitle.get(position);
    }
}
