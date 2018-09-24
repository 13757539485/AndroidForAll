package com.android.mvp.util;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.android.mvp.ipinfo.IpInfoFragment;

public class ActivityUtils {

    public static void addFragmentToActivity(FragmentManager supportFragmentManager,
                                             IpInfoFragment ipInfoFragment,
                                             int contentFrame) {
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        transaction.add(contentFrame, ipInfoFragment);
        transaction.commit();
    }
}
