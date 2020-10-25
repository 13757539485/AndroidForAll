package com.android.meituan;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.airbnb.lottie.LottieAnimationView;

public class HomeBottomView extends LinearLayoutCompat {
    private Context mContext;
    private LottieAnimationView iconSelected;
    private boolean mLastSelect = false;
    private String selectPath, unSelectPath;

    public HomeBottomView(@NonNull Context context) {
        this(context, null);
    }

    public HomeBottomView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeBottomView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        View view = View.inflate(mContext, R.layout.home_bottom_bar, this);
        iconSelected = view.findViewById(R.id.icon_selected);
    }

    public void init(String selectIconPath, String unSelectIconPath, boolean isSelect) {
        mLastSelect = isSelect;
        selectPath = selectIconPath;
        unSelectPath = unSelectIconPath;
        if (selectIconPath != null && iconSelected != null) {
            try {
                iconSelected.setCacheComposition(true);
                if (isSelect) {
                    iconSelected.setAnimation(selectIconPath);
                    iconSelected.setProgress(1);
                } else {
                    iconSelected.setAnimation(unSelectIconPath);
                    iconSelected.setProgress(1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setSelected(boolean selected) {
        if (selected) {
            if (iconSelected != null) {
                mLastSelect = true;
                iconSelected.cancelAnimation();
                iconSelected.setAnimation(selectPath);
                iconSelected.setProgress(0);
                iconSelected.playAnimation();
            }
        } else {
            if (iconSelected != null) {
                if (mLastSelect) {
                    mLastSelect = false;
                    iconSelected.cancelAnimation();
                    iconSelected.setAnimation(unSelectPath);
                    iconSelected.setProgress(0);
                    iconSelected.playAnimation();
                } else {
                    iconSelected.cancelAnimation();
                    iconSelected.setProgress(1);
                }
            }
        }
        super.setSelected(selected);
    }
}
