package com.android.mvp.main.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.android.mvp.base.XBaseFragment;
import com.android.mvp.main.contract.FileContract;
import com.android.mvp.main.presenter.FilePresenter;

import java.io.File;

public class FileFragment extends XBaseFragment implements FileContract.View {
    private FilePresenter mFilePresenter;

    @Override
    public void showFileInfo(File file) {
        Log.e("yuli", "showFileInfo: " + file);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFilePresenter = new FilePresenter(getActivity());
        mFilePresenter.setView(this);
        mFilePresenter.getFile("");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
