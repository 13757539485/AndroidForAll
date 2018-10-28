package com.android.advancesettings.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.advancesettings.R;

public class OtherFragment extends BaseFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() {

    }

    @Override
    protected int loadXml() {
        return R.xml.preference_other;
    }
}
