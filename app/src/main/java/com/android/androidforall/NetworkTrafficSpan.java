package com.android.androidforall;

import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;

public class NetworkTrafficSpan extends MetricAffectingSpan {
    double ratio = 0.0D;

    public NetworkTrafficSpan(double var1) {
        this.ratio = var1;
    }

    public void updateDrawState(TextPaint var1) {
        var1.baselineShift += (int)((double)var1.ascent() * this.ratio);
    }

    public void updateMeasureState(TextPaint var1) {
        var1.baselineShift += (int)((double)var1.ascent() * this.ratio);
    }
}