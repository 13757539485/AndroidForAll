package com.android.mvp.base;

import android.content.Intent;

public abstract class XBasePresenter<T extends XContract.View> {
    public void onCreate(Intent intent) {
    }

    public void onResume() {

    }

    public void onPause() {

    }

    public void onStop() {

    }

    public void onDestroy() {

    }

    public void onNewIntent(Intent intent) {

    }

    public void setView(T view) {

    }
}
