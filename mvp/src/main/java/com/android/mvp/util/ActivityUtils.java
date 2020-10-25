package com.android.mvp.util;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ActivityUtils {

    public static void addFragmentToActivity(FragmentManager supportFragmentManager,
                                             Fragment fragment,
                                             int contentFrame) {
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        transaction.add(contentFrame, fragment);
        transaction.commit();
    }
}
