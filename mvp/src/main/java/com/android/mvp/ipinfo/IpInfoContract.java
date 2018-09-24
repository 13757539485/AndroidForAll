package com.android.mvp.ipinfo;

import com.android.mvp.BaseView;
import com.android.mvp.model.IpInfo;

/**
 * 定义获取数据方法
 */
public interface IpInfoContract {
    interface Presenter{
        void getIpInfo(String ip);
    }

    interface View extends BaseView<Presenter> {
        void setIpInfo(IpInfo ipInfo);

        void showLoading();

        void hideLoading();

        void showError(int errorCode, String msg);

        boolean isActive();//判断fragment是否添加到activity中
    }

}
