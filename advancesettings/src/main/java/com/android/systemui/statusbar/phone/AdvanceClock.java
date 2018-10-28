package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextClock;

import com.android.systemui.statusbar.policy.DarkDicher;
import com.android.systemui.statusbar.policy.DarkReceiver;

public class AdvanceClock extends TextClock implements DarkReceiver {
    public AdvanceClock(Context context) {
        super(context);
    }

    public AdvanceClock(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AdvanceClock(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onDarkChanged(Rect area, float darkIntensity, int i) {
        //setTextColor(getResources().getColor(DarkIconDispatcher.inDarkMode(area, this, darkIntensity) ? 0x7f060195 : 0x7f060194));
        DarkDicher.p();
    }
}
