package com.android.jatpack.lifedata;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * @author 俞梨
 * @description:
 * @date :2021/9/12 20:30
 */
public class NameViewModel extends ViewModel {
    private MutableLiveData<String> mName;

    public MutableLiveData<String> getCurrentName() {
        if (mName == null) {
            mName = new MutableLiveData<>();
        }
        return mName;
    }
}
