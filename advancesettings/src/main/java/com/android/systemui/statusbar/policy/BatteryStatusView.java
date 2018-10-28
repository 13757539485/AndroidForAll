package com.android.systemui.statusbar.policy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

import com.android.advancesettings.R;

//import com.android.systemui.R;

public class BatteryStatusView extends TextView implements DarkReceiver {

    private BatteryReceiver mBatteryReceiver;
    private Context mContext;

    public BatteryStatusView(Context context) {
        this(context,null);
    }

    public BatteryStatusView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BatteryStatusView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mBatteryReceiver = new BatteryReceiver();
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        context.registerReceiver(mBatteryReceiver, intentFilter);
    }

    @Override
    public void onDarkChanged(Rect rect, float f, int i) {

    }

    private class BatteryReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (Intent.ACTION_BATTERY_CHANGED.equals(action)) {
                int temperature = intent.getIntExtra("temperature", 0) / 10;
                setText(context.getResources().getQuantityString(R.plurals.tempuare, temperature, temperature));
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mBatteryReceiver != null) {
            mContext.unregisterReceiver(mBatteryReceiver);
            mBatteryReceiver = null;
        }
    }
}
