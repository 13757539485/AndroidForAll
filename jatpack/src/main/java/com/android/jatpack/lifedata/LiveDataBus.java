package com.android.jatpack.lifedata;

import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 俞梨
 * @description:
 * @date :2021/9/12 23:14
 */
public class LiveDataBus {
    //存放订阅者
    private Map<String, MutableLiveData<Object>> mBus;
    private static LiveDataBus mLiveDataBus = new LiveDataBus();

    private LiveDataBus() {
        mBus = new HashMap<>();
    }

    public static LiveDataBus getInstance() {
        return mLiveDataBus;
    }

    //注册订阅者
    public synchronized <T> MutableLiveData<T> with(String key, Class<T> type) {
        if (!mBus.containsKey(key)) {
            mBus.put(key, new MutableLiveData<>());
        }
        return (MutableLiveData<T>) mBus.get(key);
    }
}
