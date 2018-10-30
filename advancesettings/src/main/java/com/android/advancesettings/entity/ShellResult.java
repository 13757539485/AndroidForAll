package com.android.advancesettings.entity;

public class ShellResult {
    private int result;
    private String errorMsg;
    private String successMsg = "应用成功";

    public void setResult(int result) {
        this.result = result;
    }

    public int getResult() {
        return result;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getSuccessMsg() {
        return successMsg;
    }

    @Override
    public String toString() {
        return "ShellResult{" +
                "result=" + result +
                ", errorMsg='" + errorMsg + '\'' +
                ", successMsg='" + successMsg + '\'' +
                '}';
    }
}
