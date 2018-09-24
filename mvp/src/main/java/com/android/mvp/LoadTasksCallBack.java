package com.android.mvp;

/**
 * 网络访问回调监听
 * @param <T>
 */
public interface LoadTasksCallBack<T> {
    void onSuccess(T t);

    void onStart();

    void onFailed(int errorCode, String msg);

    void onFinish();
}
