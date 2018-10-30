package com.android.androidforall;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;

public class MainActivity extends Activity {
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
