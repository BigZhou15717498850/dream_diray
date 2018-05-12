package com.example.ly309313.demo_database.utils;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.google.common.annotations.VisibleForTesting;

import java.util.concurrent.Executor;

/**
 * 作者 LY309313
 * 日期 2018/5/12
 * 描述
 */

public class AppExecutors {

    private static AppExecutors INSTANCE;

    private Executor diskIO;

    private Executor mainThread;

    @VisibleForTesting
    AppExecutors(Executor diskIO, Executor mainThread) {
        this.diskIO = diskIO;
        this.mainThread = mainThread;
    }

    public AppExecutors(){
        this(new DiskIOThreadExecutors(),new MainThreadExecutors());
    }

    public Executor diskIO(){
        return diskIO;
    }

    public Executor mainThread(){
        return mainThread;
    }


    private static class MainThreadExecutors implements Executor {
        private Handler mHandler;
        public MainThreadExecutors(){
            mHandler = new Handler(Looper.getMainLooper());
        }
        @Override
        public void execute(@NonNull Runnable runnable) {
            mHandler.post(runnable);
        }
    }
}
