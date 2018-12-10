package com.android.mvp.main.contract;

import com.android.mvp.base.XContract;

import java.io.File;

public interface FileContract {
    interface View extends XContract.View {
        void showFileInfo(File file);
    }

    interface Presenter extends XContract.Presenter {
        void getFile(String path);
    }

    interface Model extends XContract.Model {
        File loadFile();
    }
}
