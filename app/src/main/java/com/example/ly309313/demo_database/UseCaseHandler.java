package com.example.ly309313.demo_database;

/**
 * 作者 LY309313
 * 日期 2018/5/12
 * 描述
 */

public class UseCaseHandler {

    private static UseCaseHandler sInstance;

    private UseCaseScheduler mUseCaseScheduler;


    public UseCaseHandler(UseCaseScheduler useCaseScheduler) {
        mUseCaseScheduler = useCaseScheduler;
    }

    public <Q extends UseCase.RequestValues,P extends UseCase.ResponseValues> void execute(
            final UseCase<Q,P> useCase, Q values,
            UseCase.UseCaseCallback<P> callback){
            useCase.setRequsetValues(values);
            useCase.setUseCaseCallback(new UIUseCaseCallbackWrapper(callback));

            //所有用例都在子线程完成，结果在ui线程展示
            mUseCaseScheduler.execute(new Runnable() {
                @Override
                public void run() {

                    useCase.run();
                }
            });
    }

    public <V extends UseCase.ResponseValues> void notifyResponse(UseCase.UseCaseCallback<V> caseCallback,V response){
        mUseCaseScheduler.notifyResponse(caseCallback,response);
    }

    public <V extends UseCase.ResponseValues> void notifyError(UseCase.UseCaseCallback<V> caseCallback){
        mUseCaseScheduler.notifyError(caseCallback);
    }

    public final class UIUseCaseCallbackWrapper<V extends UseCase.ResponseValues> implements UseCase.UseCaseCallback<V>{

         private UseCase.UseCaseCallback<V> mUseCaseCallback;

         private UseCaseHandler INSTANCE;
        public UIUseCaseCallbackWrapper(UseCase.UseCaseCallback<V> useCaseCallback) {
            mUseCaseCallback = useCaseCallback;
            INSTANCE = getInstance();
        }

        @Override
        public void onSuccess(V response) {
            INSTANCE.notifyResponse(mUseCaseCallback,response);
        }

        @Override
        public void onError() {
            INSTANCE.notifyError(mUseCaseCallback);
        }
    }

    public static UseCaseHandler getInstance() {
        if(sInstance==null){
            sInstance = new UseCaseHandler(new UseCaseThreadPoolScheduler());
        }
        return sInstance;
    }
}
