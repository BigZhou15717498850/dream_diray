package com.example.ly309313.demo_database;

/**
 * 作者 LY309313
 * 日期 2018/5/12
 * 描述
 */

public interface UseCaseScheduler {

    void execute(Runnable runnable);


    <P extends UseCase.ResponseValues> void  notifyResponse(UseCase.UseCaseCallback<P> caseCallback, P response);

    <P extends UseCase.ResponseValues> void  notifyError(UseCase.UseCaseCallback<P> caseCallback);
}
