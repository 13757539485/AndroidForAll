package com.android.advancesettings.utils;

import android.util.Log;

import com.android.advancesettings.entity.ShellResult;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ShellUtils {

    public static final String TAG = "ShellUtils";

    public final static String COMMAND_SU       = "su";
    public final static String COMMAND_SH       = "sh ";
    public final static String COMMAND_EXIT     = "exit\n";
    public final static String COMMAND_LINE_END = "\n";
    public final static String COMMAND_MOUNT_SYSTEM = "mount -o rw,remount /system && chmod 755 ";

    /**
     * 执行命令—单条
     * @param command
     * @return
     */
    public static ShellResult execCommand(String command) {
        String[] commands = {command};
        return execCommand(commands);
    }

    public static ShellResult execSh(String shPath) {
        ShellResult shellResult = new ShellResult();
        BufferedReader successResult = null;
        BufferedReader errorResult = null;
        Process process = null;
        DataOutputStream execDos = null;
        try {
            process = Runtime.getRuntime().exec(COMMAND_SU);
            execDos = new DataOutputStream(process.getOutputStream());
            execDos.writeBytes(COMMAND_SH + shPath + "\n");
            execDos.flush();
            successResult = new BufferedReader(new InputStreamReader(process.getInputStream()));
            errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            StringBuilder successBuilder = new StringBuilder();
            StringBuilder errorBuilder = new StringBuilder();
            String temp;
            while ((temp = successResult.readLine()) != null) successBuilder.append(temp);
            while ((temp = errorResult.readLine()) != null) errorBuilder.append(temp);
            shellResult.successMsg = successBuilder.toString();
            shellResult.errorMsg = errorBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (successResult != null) successResult.close();
                if (errorResult != null) errorResult.close();
                if (execDos != null) execDos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (process != null) process.destroy();
        }
        return shellResult;
    }

    /**
     * 执行命令-多条
     * @param commands
     * @return
     */
    public static ShellResult execCommand(String[] commands) {
        ShellResult shellResult = new ShellResult();
        Process process = null;
        DataOutputStream os = null;
        BufferedReader successResult = null;
        BufferedReader errorResult = null;
        StringBuilder successMsg;
        StringBuilder errorMsg;
        try {
            process = Runtime.getRuntime().exec(COMMAND_SU);
            os = new DataOutputStream(process.getOutputStream());
            for (String command : commands) {
                if (command != null) {
                    os.write(command.getBytes());
                    os.writeBytes(COMMAND_LINE_END);
                    os.flush();
                }
            }
            os.writeBytes(COMMAND_EXIT);
            os.flush();
            //获取错误信息
            successMsg = new StringBuilder();
            errorMsg = new StringBuilder();
            successResult = new BufferedReader(new InputStreamReader(process.getInputStream()));
            errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String s;
            while ((s = successResult.readLine()) != null) successMsg.append(s);
            while ((s = errorResult.readLine()) != null) errorMsg.append(s);
            shellResult.successMsg = successMsg.toString();
            shellResult.errorMsg = errorMsg.toString();
            Log.i(TAG, shellResult.successMsg
                    + " | " + shellResult.errorMsg);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) os.close();
                if (successResult != null) successResult.close();
                if (errorResult != null) errorResult.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (process != null) process.destroy();
        }
        return shellResult;
    }

    public static boolean isRoot() {
        boolean retval;
        Process suProcess;
        try {
            suProcess = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(suProcess.getOutputStream());
            DataInputStream osRes = new DataInputStream(suProcess.getInputStream());
            // Getting the id of the current user to check if this is root
            os.writeBytes("id\n");
            os.flush();
            String currUid = osRes.readLine();
            boolean exitSu;
            if (null == currUid) {
                retval = false;
                exitSu = false;
                Log.d("ROOT", "Can't get root access or denied by user");
            } else if (currUid.contains("uid=0")) {
                retval = true;
                exitSu = true;
                Log.d("ROOT", "Root access granted");
            } else {
                retval = false;
                exitSu = true;
                Log.d("ROOT", "Root access rejected: " + currUid);
            }
            if (exitSu) {
                os.writeBytes("exit\n");
                os.flush();
            }
        } catch (Exception e) {
            // Can't get root !
            // Probably broken pipe exception on trying to write to output
            // stream after su failed, meaning that the device is not rooted

            retval = false;
            Log.d("ROOT",
                    "Root access rejected [" + e.getClass().getName() + "] : " + e.getMessage());
        }
        return retval;
    }

    public static String catCommand(String command) throws IOException {
        Process process = Runtime.getRuntime().exec("su");
        DataOutputStream os = new DataOutputStream(process.getOutputStream());
        DataInputStream input = new DataInputStream(process.getInputStream());
        os.writeBytes(command + "\n");
        os.writeBytes("exit\n");
        os.flush();
        return input.readLine();
    }
}
