package com.android.androidforall;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.cardview.widget.CardView;
import android.view.View;

public class Main2Activity extends Activity {

    private CardView n;
    private Handler.Callback mCallback = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            return true;
        }
    };
    private WeakRefHandler mHandler = new WeakRefHandler(mCallback);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() { /* ... */ }
        }, 1000 * 60 * 10);

        // Go back to the previous Activity.
        finish();
    }

    private void initView() {
    }

    private void setBackgroundColor(CardView cardView, boolean isSelect) {
        cardView.setSelected(isSelect);
    }

    private void a(CardView cardView, int i, int i2) {
        if (this.n != cardView) {
            setBackgroundColor(cardView, true);
            if (this.n != null) {
                setBackgroundColor(this.n, false);
            }
            this.n = cardView;
        }
    }

    public void goA(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    protected void onDestroy() {
//        mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

}
