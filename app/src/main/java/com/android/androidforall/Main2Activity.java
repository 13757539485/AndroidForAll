package com.android.androidforall;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.animation.OvershootInterpolator;

import androidx.appcompat.widget.AppCompatImageView;

public class Main2Activity extends Activity {
    private int count = 0;
    private int[] redBgResIds = {R.mipmap.red_cout_down_five, R.mipmap.red_cout_down_four,
            R.mipmap.red_cout_down_three, R.mipmap.red_cout_down_two,
            R.mipmap.red_cout_down_one};
    private AppCompatImageView mRedCountDownImgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mRedCountDownImgView = findViewById(R.id.red_count_down_img);
        AnimatorSet redImgSet = new AnimatorSet();
        ValueAnimator redImgSX = ObjectAnimator.ofFloat(mRedCountDownImgView, "scaleX", 4f, 1f);
        ValueAnimator redImgSY = ObjectAnimator.ofFloat(mRedCountDownImgView, "scaleY", 4f, 1f);
        ValueAnimator redAlpha = ObjectAnimator.ofFloat(mRedCountDownImgView, "alpha", 0f, 1f);
        redAlpha.setRepeatCount(4);
        redAlpha.setRepeatMode(ValueAnimator.RESTART);
        redImgSX.setRepeatCount(4);
        redImgSX.setRepeatMode(ValueAnimator.RESTART);
        redImgSY.setRepeatCount(4);
        redImgSY.setRepeatMode(ValueAnimator.RESTART);
        redImgSet.playTogether(redAlpha, redImgSX, redImgSY);
        redImgSet.setDuration(1000);
        redImgSet.setStartDelay(1000);
        redImgSet.setInterpolator(new OvershootInterpolator());
//        redImgSet.setInterpolator(new AnticipateInterpolator());
        redImgSX.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                mRedCountDownImgView.setImageResource(redBgResIds[count]);
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                count++;
                mRedCountDownImgView.setImageResource(redBgResIds[count]);

            }
        });
        redImgSet.start();
    }

}
