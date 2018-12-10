package com.android.mvp.main.model;

import android.os.Environment;

import com.android.mvp.main.contract.FileContract;

import java.io.File;

public class FileModel implements FileContract.Model {

    @Override
    public File loadFile() {
        File file = new File(Environment.getExternalStorageDirectory().getPath() + "123.sh");
        if (file.exists()) {
            return file;
        }else {
            return null;
        }
    }
}
