package com.android.server.policy;

import android.os.Handler;

/**
 * @author 俞梨
 * @description:
 * @date :2021/10/2 22:00
 */
public class PhoneWindowManager {
    private boolean enable;
    private Handler mHandler;
    private long mLockScreenTimeout;
    private void updateLockScreenTimeout() {
        if (enable) {
            this.mHandler.postDelayed(null, mLockScreenTimeout);
        } else {
            this.mHandler.removeCallbacks(null);
        }
    }

    private void updateLockScreenTimeout1() {
        if (enable) {
            this.mHandler.removeCallbacks(null);
            if (mLockScreenTimeout != 0) {
                this.mHandler.postDelayed(null, mLockScreenTimeout);
            }
        } else {
            this.mHandler.removeCallbacks(null);
        }
    }
}
