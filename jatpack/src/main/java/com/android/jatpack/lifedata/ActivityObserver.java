package com.android.jatpack.lifedata;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * @author 俞梨
 * @description:
 * @date :2021/9/12 17:03
 */
public class ActivityObserver implements LifecycleObserver {

    private static final String TAG = ActivityObserver.class.getSimpleName();

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreateX(LifecycleOwner owner) {
        Log.d(TAG, "onCreateX: ");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void onStartX(LifecycleOwner owner) {
        Log.d(TAG, "onStartX: ");
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void onStop(LifecycleOwner owner) {
        Log.d(TAG, "onStop: ");
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void onResume(LifecycleOwner owner) {
        Log.d(TAG, "onResume: ");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void onPause(LifecycleOwner owner) {
        Log.d(TAG, "onPause: ");
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestory(LifecycleOwner owner) {
        Log.d(TAG, "onDestory: ");
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    void onAny(LifecycleOwner owner) {
        Log.d(TAG, "onAny: ");
    }
}
