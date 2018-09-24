package com.android.mvp.net;

import com.android.mvp.LoadTasksCallBack;

/**
 * 获取网络数据的接口
 * @param <T>
 */
public interface NetTask<T> {
    void execute(T data, LoadTasksCallBack callBack);
}
