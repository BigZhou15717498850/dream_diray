package com.example.ly309313.demo_database;

import android.os.Handler;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 作者 LY309313
 * 日期 2018/5/12
 * 描述
 */

public class UseCaseThreadPoolScheduler implements UseCaseScheduler{

    private Handler mHandler = new Handler();

    private ThreadPoolExecutor mThreadPoolExecutor;

    private static final int POOL_CORE_SIZE = 2;

    private static final int POOL_MAX_SIZE = 4;

    private static final int POOL_TIMEOUT = 30;

    public UseCaseThreadPoolScheduler() {
        mThreadPoolExecutor = new ThreadPoolExecutor(POOL_CORE_SIZE,POOL_MAX_SIZE,POOL_TIMEOUT, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(POOL_CORE_SIZE));
    }

    @Override
    public void execute(Runnable runnable) {
        mThreadPoolExecutor.execute(runnable);
    }

    @Override
    public <P extends UseCase.ResponseValues> void notifyResponse(final UseCase.UseCaseCallback<P> caseCallback, final P response) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                caseCallback.onSuccess(response);
            }
        });
    }

    @Override
    public <P extends UseCase.ResponseValues> void notifyError(final UseCase.UseCaseCallback<P> caseCallback) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                caseCallback.onError();
            }
        });
    }
}
