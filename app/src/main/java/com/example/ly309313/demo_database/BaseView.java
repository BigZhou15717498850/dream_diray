package com.example.ly309313.demo_database;

/**
 * Created by LY309313 on 2018/5/11.
 */

public interface BaseView<T extends BasePresenter> {

    void setPresenter(T presenter);
}
