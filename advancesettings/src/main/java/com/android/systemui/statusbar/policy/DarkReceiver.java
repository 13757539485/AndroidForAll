package com.android.systemui.statusbar.policy;

import android.graphics.Rect;

public interface DarkReceiver {
    void onDarkChanged(Rect rect, float f, int i);
}
