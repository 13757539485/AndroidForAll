package com.android.mvp.ipinfo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.mvp.util.ActivityUtils;
import com.android.mvp.net.IpInfoTask;
import com.android.mvp.R;

public class IpInfoActivity extends AppCompatActivity {
    private IpInfoPresenter mIpInfoPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip_info);
        IpInfoFragment ipInfoFragment = IpInfoFragment.newInstance();
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    ipInfoFragment, R.id.contentFrame);
        IpInfoTask ipInfoTask = IpInfoTask.getInstance();
        mIpInfoPresenter = new IpInfoPresenter(ipInfoTask, ipInfoFragment);
        ipInfoFragment.setPresenter(mIpInfoPresenter);
    }
}
