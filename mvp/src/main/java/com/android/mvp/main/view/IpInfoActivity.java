package com.android.mvp.main.view;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.android.mvp.R;
import com.android.mvp.util.ActivityUtils;

public class IpInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip_info);
//        IpInfoFragment ipInfoFragment = IpInfoFragment.newInstance();
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    new FileFragment(), R.id.contentFrame);
        /*IpInfoTask ipInfoTask = IpInfoTask.getInstance();
        mIpInfoPresenter = new IpInfoPresenter(ipInfoTask, ipInfoFragment);
        ipInfoFragment.setPresenter(mIpInfoPresenter);*/
    }
}
