package com.android.androidforall;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnOnClick(View view) {
        try {
            Process process = Runtime.getRuntime().exec("su");
            DataOutputStream localDataOutputStream = new DataOutputStream(
                    process.getOutputStream());
            localDataOutputStream.writeBytes("sh  /sdcard/1/123.sh\n");
            localDataOutputStream.flush();
            localDataOutputStream.close();
            BufferedReader successResult = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            String success = successResult.readLine();
            String error = errorResult.readLine();
            if (success == null) {
                showDialog(error);
            } else {
                showDialog(success);
            }
        } catch (Exception e) {

        }
    }

    private void showDialog(String string) {
        new AlertDialog.Builder(this).
                setTitle("出错了").
                setMessage(string).
                setPositiveButton("确定", null).show();
    }
}
