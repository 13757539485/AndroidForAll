package com.android.androidforall;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

class SignalClusterView extends LinearLayout{
    private ViewGroup mWifiGroupView;
    private ImageView mWifiApView;
    private RelativeLayout[] mMobileVivos;

    public SignalClusterView(Context context) {
        super(context);
    }

    public SignalClusterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SignalClusterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ViewGroup getWifiGroupView() {
        return mWifiGroupView;
    }

    public ImageView getWifiApView() {
        return mWifiApView;
    }

    public RelativeLayout[] getMobileVivos() {
        return mMobileVivos;
    }
}
