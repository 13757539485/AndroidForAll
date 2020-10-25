package com.android.advancesettings.fragments;

import android.os.Bundle;
import androidx.annotation.Nullable;

import com.android.advancesettings.R;

public class SystemUIFragment extends BaseFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() {

    }

    @Override
    protected int loadXml() {
        return R.xml.preference_systemui;
    }
}
