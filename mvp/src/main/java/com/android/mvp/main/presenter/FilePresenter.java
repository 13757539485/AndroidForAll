package com.android.mvp.main.presenter;


import android.content.Context;

import com.android.mvp.base.XBasePresenter;
import com.android.mvp.main.contract.FileContract;
import com.android.mvp.main.model.FileModel;

import java.io.File;

public class FilePresenter extends XBasePresenter<FileContract.View> implements FileContract.Presenter {
    private FileContract.View mView;
    private Context mContext;
    private FileModel mFileModel;

    public FilePresenter(Context context) {
        mContext = context;
        mFileModel = new FileModel();
    }

    @Override
    public void getFile(String path) {
        File file = mFileModel.loadFile();
        mView.showFileInfo(file);
    }

    @Override
    public void setView(FileContract.View view) {
        mView = view;
    }
}
