package com.example.ly309313.demo_database;

/**
 * 作者 LY309313
 * 日期 2018/5/12
 * 描述
 */

public abstract class UseCase<Q extends UseCase.RequestValues,P extends UseCase.ResponseValues> {

    private Q mRequsetValues;

    private UseCaseCallback mUseCaseCallback;

    public Q getRequsetValues() {
        return mRequsetValues;
    }

    public void setRequsetValues(Q requsetValues) {
        mRequsetValues = requsetValues;
    }

    public UseCaseCallback getUseCaseCallback() {
        return mUseCaseCallback;
    }

    public void setUseCaseCallback(UseCaseCallback useCaseCallback) {
        mUseCaseCallback = useCaseCallback;
    }


    public void run(){
        executeUseCase(mRequsetValues);
    }

    protected abstract void executeUseCase(Q requsetValues);

    public interface RequestValues{}

    public interface ResponseValues{}

    public interface UseCaseCallback<R>{
        void onSuccess(R response);

        void onError();
    }
}
