package com.android.mvp.main.contract;

import com.android.mvp.BaseView;
import com.android.mvp.bean.IpInfo;

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
