package com.android.mvp;

/**
 * 用于View绑定Presenter
 * @param <T>
 */
public interface BaseView<T> {
    void setPresenter(T presenter);
}
