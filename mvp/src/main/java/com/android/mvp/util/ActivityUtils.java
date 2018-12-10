package com.android.mvp.util;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;


public class ActivityUtils {

    public static void addFragmentToActivity(FragmentManager supportFragmentManager,
                                             Fragment fragment,
                                             int contentFrame) {
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        transaction.add(contentFrame, fragment);
        transaction.commit();
    }
}
