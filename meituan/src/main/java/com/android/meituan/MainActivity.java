package com.android.meituan;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView mPagerContainer;
    HomeBottomView mBottomHomeTab;
    HomeBottomView mBottomVipTab;
    HomeBottomView mBottomOrderTab;
    HomeBottomView mBottomMyTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        mBottomHomeTab.init("wm_main_tab_select_home.json",
                "wm_main_tab_normal_home.json", true);
        mBottomVipTab.init("wm_main_tab_select_vip.json",
                "wm_main_tab_normal_vip.json", false);
        mBottomOrderTab.init("wm_main_tab_select_order.json",
                "wm_main_tab_normal_order.json", false);
        mBottomMyTab.init("wm_main_tab_select_mine.json",
                "wm_main_tab_normal_mine.json", false);
    }

    private void initView() {
        mPagerContainer = findViewById(R.id.pager_container);
        mBottomHomeTab = findViewById(R.id.bottom_home_tab);
        mBottomHomeTab.setOnClickListener(this);
        mBottomVipTab = findViewById(R.id.bottom_vip_tab);
        mBottomVipTab.setOnClickListener(this);
        mBottomOrderTab = findViewById(R.id.bottom_order_tab);
        mBottomOrderTab.setOnClickListener(this);
        mBottomMyTab = findViewById(R.id.bottom_my_tab);
        mBottomMyTab.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bottom_home_tab:
                if (!mBottomHomeTab.isSelected()) {
                    mBottomHomeTab.setSelected(true);
                    mBottomVipTab.setSelected(false);
                    mBottomOrderTab.setSelected(false);
                    mBottomMyTab.setSelected(false);
                }
                break;
            case R.id.bottom_vip_tab:
                if (!mBottomVipTab.isSelected()) {
                    mBottomHomeTab.setSelected(false);
                    mBottomVipTab.setSelected(true);
                    mBottomOrderTab.setSelected(false);
                    mBottomMyTab.setSelected(false);
                }
                break;
            case R.id.bottom_order_tab:
                if (!mBottomOrderTab.isSelected()) {
                    mBottomHomeTab.setSelected(false);
                    mBottomVipTab.setSelected(false);
                    mBottomOrderTab.setSelected(true);
                    mBottomMyTab.setSelected(false);
                }
                break;
            case R.id.bottom_my_tab:
                if (!mBottomMyTab.isSelected()) {
                    mBottomHomeTab.setSelected(false);
                    mBottomVipTab.setSelected(false);
                    mBottomOrderTab.setSelected(false);
                    mBottomMyTab.setSelected(true);
                }
                break;
        }
    }
}
