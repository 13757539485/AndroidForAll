package com.android.androidforall;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getName();
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case 100:
                    String shellResult = (String) msg.obj;
                    showDialog(shellResult);
                    break;
            }
            return false;
        }
    });
    private int mSlot = 0;
    private View mMobileSignalUpgrade;
    private TextView mMobileType;
    private RelativeLayout mMobileVivo;
    private LinearLayout[] mMobileSignalGroup;
    private SignalClusterView mSignalClusterView;
    private LinearLayout mCustomLocation;
    private LinearLayout mMiuiLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        setContentView(getMobileInoutLayout());
        //mMobileVivo = findViewById(R.id.mobile_vivo);
        //mMiuiLayout = findViewById(R.id.miui_layout);
        //mMiuiLayout.removeView(mMobileVivo);
        Log.d(TAG, "onCreate: ");

    }

    private static class MyTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: ");
        super.onResume();
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart: ");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        Log.d(TAG, "onRestart: ");
        super.onRestart();
    }

    public ViewGroup getMobileInoutLayout() {
        LinearLayout first = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        first.setLayoutParams(params);
        first.setGravity(Gravity.CENTER_HORIZONTAL);
        first.setOrientation(LinearLayout.VERTICAL);
        LinearLayout second = new LinearLayout(this);
        second.setLayoutParams(params);
        second.setOrientation(LinearLayout.HORIZONTAL);
        TextView textView1 = new TextView(this);
        textView1.setText("4G");
        TextView textView2 = new TextView(this);
        textView2.setText("+");
        second.addView(textView1);
        second.addView(textView2);
        TextView textView3 = new TextView(this);
        textView3.setText("↑↓");
        first.addView(second);
        first.addView(textView3,params);
        return first;
    }

   /* public ViewGroup getMobileInoutLayout() {
        RelativeLayout temp = new RelativeLayout(this);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMarginStart(1);
        temp.setLayoutParams(layoutParams);
        TextView textView1 = new TextView(this);
        textView1.setText("4G");
        textView1.setId(100);
        TextView textView2 = new TextView(this);
        textView2.setText("+");
        RelativeLayout.LayoutParams child2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        child2.addRule(RelativeLayout.END_OF,textView1.getId());
        TextView textView3 = new TextView(this);
        textView3.setText("↑↓");
        RelativeLayout.LayoutParams child3 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        child3.addRule(RelativeLayout.BELOW,textView1.getId());
        child3.addRule(RelativeLayout.CENTER_HORIZONTAL,RelativeLayout.TRUE);
        temp.addView(textView1);
        temp.addView(textView2,child2);
        temp.addView(textView3,child3);
        return temp;
    }*/


    public RelativeLayout[] getMobileVivos() {
        RelativeLayout[] relativeLayouts = new RelativeLayout[2];
        relativeLayouts[0] = mMobileSignalGroup[0].findViewById(R.id.mobile_vivo);
        relativeLayouts[1] = mMobileSignalGroup[1].findViewById(R.id.mobile_vivo);
        return relativeLayouts;
    }

    protected void replaceIconPosition() {
        ViewGroup wifiGroupView = this.mSignalClusterView.getWifiGroupView();
        ImageView wifiApView = this.mSignalClusterView.getWifiApView();
        RelativeLayout[] mobileVivos = this.mSignalClusterView.getMobileVivos();
        RelativeLayout mobileVivo0 = mobileVivos[0];
        RelativeLayout mobileVivo1 = mobileVivos[1];
        this.mSignalClusterView.removeView(mobileVivo0);
        this.mSignalClusterView.removeView(mobileVivo1);
        this.mSignalClusterView.removeView(wifiGroupView);
        this.mSignalClusterView.removeView(wifiApView);
        /*this.mCustomLocation.addView(mobileVivo0);
        this.mCustomLocation.addView(mobileVivo1);*/
        this.mCustomLocation.addView(wifiGroupView);
        this.mCustomLocation.addView(wifiApView);
        Log.d("yuli", "array: " + Arrays.toString(mobileVivos));
    }

    private boolean updateMobileType(String str) {
        if (str.equals("4G+")) {
            if (false) {
            } else if (this.mMobileSignalUpgrade.getVisibility() != View.VISIBLE) {
                setMobileType(this.mMobileType, "4G", mSlot);
            }
            return true;
        } else if (true) {
            setMobileType(this.mMobileType, "4G", mSlot);
            return false;
        } else if (true) {
            return false;
        } else {
            return false;
        }
    }


    private void setMobileType(TextView textView, String type, int slot) {
        SpannableString spannableString = new SpannableString(type + (slot + 1));
        RelativeSizeSpan slotTextSize = new RelativeSizeSpan(0.7f);
        int length = spannableString.length();
        spannableString.setSpan(slotTextSize, length - 1, length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString);
    }

    public void printLog(String string) {
        Log.d("yuli", "array: " + string);
    }

    public void btnOnClick(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                String exec = exec();
                Message message = mHandler.obtainMessage();
                message.obj = exec;
                message.what = 100;
                mHandler.sendMessage(message);
            }
        }).start();
    }

    private String exec() {
        String str = "";
        try {
            Process process = Runtime.getRuntime().exec("su");
            DataOutputStream localDataOutputStream = new DataOutputStream(
                    process.getOutputStream());
            localDataOutputStream.writeBytes("sh /system/123.sh\n");
            localDataOutputStream.flush();
            localDataOutputStream.close();
            BufferedReader successResult = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            String success = successResult.readLine();
            String error = errorResult.readLine();
            if (success == null) {
                str = success;
            } else {
                str = error;
            }
        } catch (Exception e) {

        }
        return str;
    }

    private void showDialog(String string) {
        new AlertDialog.Builder(this).
                setTitle("出错了").
                setMessage(string).
                setPositiveButton("确定", null).show();
    }
}
