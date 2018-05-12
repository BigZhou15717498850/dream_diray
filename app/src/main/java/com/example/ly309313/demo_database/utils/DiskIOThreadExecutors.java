package com.example.ly309313.demo_database.utils;

import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 作者 LY309313
 * 日期 2018/5/12
 * 描述
 */

class DiskIOThreadExecutors implements Executor {

    private Executor diskIO;

    public DiskIOThreadExecutors(){
        this.diskIO = Executors.newSingleThreadExecutor();
    }

    @Override
    public void execute(@NonNull Runnable runnable) {
        diskIO.execute(runnable);
    }
}
