package com.android.mvp.main.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.mvp.bean.IpInfo;
import com.android.mvp.R;
import com.android.mvp.main.contract.IpInfoContract;

public class IpInfoFragment extends Fragment implements IpInfoContract.View {


    /**
     * Hello blank fragment
     */
    private TextView mTvCountry;
    /**
     * Hello blank fragment
     */
    private TextView mTvArea;
    /**
     * Hello blank fragment
     */
    private TextView mTvCity;
    private Button mBtIpinfo;
    private ProgressDialog mDialog;
    private IpInfoContract.Presenter mPresenter;

    public static IpInfoFragment newInstance() {
        return new IpInfoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_ip_info, container, false);
        mTvCountry = (TextView) root.findViewById(R.id.tv_country);
        mTvArea = (TextView) root.findViewById(R.id.tv_area);
        mTvCity = (TextView) root.findViewById(R.id.tv_city);
        mBtIpinfo = (Button) root.findViewById(R.id.bt_ipinfo);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDialog = new ProgressDialog(getActivity());
        mDialog.setTitle("获取数据中...");
        mBtIpinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getIpInfo("39.155.84.147");
            }
        });
    }

    @Override
    public void setIpInfo(IpInfo ipInfo) {
        if (ipInfo != null && ipInfo.getData() != null) {
            IpInfo.IpData ipData = ipInfo.getData();
            mTvCountry.setText(ipData.getCountry());
            mTvArea.setText(ipData.getArea());
            mTvCity.setText(ipData.getCity());
        }
    }

    @Override
    public void showLoading() {
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    @Override
    public void showError(int errorCode, String msg) {
        hideLoading();
        Toast.makeText(getActivity(), "网络出错 errorCode: "+errorCode +"," + msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(IpInfoContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
