package com.android.mvp.ipinfo;

import com.android.mvp.model.IpInfo;
import com.android.mvp.LoadTasksCallBack;
import com.android.mvp.net.NetTask;

/**
 * 实现Presenter接口
 */
public class IpInfoPresenter implements IpInfoContract.Presenter, LoadTasksCallBack<IpInfo> {
    private NetTask<String> mNetTask;
    private IpInfoContract.View mAddTaskView;

    public IpInfoPresenter(NetTask netTask, IpInfoContract.View addTaskView) {
        mNetTask = netTask;
        mAddTaskView = addTaskView;
    }

    @Override
    public void getIpInfo(String ip) {
        mNetTask.execute(ip, this);
    }

    @Override
    public void onSuccess(IpInfo ipInfo) {
        if (mAddTaskView.isActive()) {
            mAddTaskView.setIpInfo(ipInfo);
        }
    }

    @Override
    public void onStart() {
        if (mAddTaskView.isActive()) {
            mAddTaskView.showLoading();
        }
    }

    @Override
    public void onFailed(int errorCode, String msg) {
        if (mAddTaskView.isActive()) {
            mAddTaskView.showError(errorCode, msg);
        }
    }

    @Override
    public void onFinish() {
        if (mAddTaskView.isActive()) {
            mAddTaskView.hideLoading();
        }
    }
}
