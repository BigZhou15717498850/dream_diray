package com.example.ly309313.demo_database.data.source;

import android.support.annotation.NonNull;

import com.example.ly309313.demo_database.tasks.domain.model.Task;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 作者 LY309313
 * 日期 2018/5/12
 * 描述
 */

public class TasksReposity implements TasksDataSource {


    private static TasksReposity INSTANCE;

    private final TasksDataSource mTasksLocalDataSource;

    private final TasksDataSource mTasksRemoteDataSource;

    Map<String,Task> mCaches;

    boolean mCacheIsDirty;

    private TasksReposity(@NonNull TasksDataSource mTasksLocalDataSource,
                          @NonNull TasksDataSource mTasksRemoteDataSource){
        this.mTasksLocalDataSource = mTasksLocalDataSource;
        this.mTasksRemoteDataSource = mTasksRemoteDataSource;
    }

    public static TasksReposity getInstance(@NonNull TasksDataSource mTasksLocalDataSource,
                                            @NonNull TasksDataSource mTasksRemoteDataSource){
        if(INSTANCE ==null){
            INSTANCE = new TasksReposity(mTasksLocalDataSource,mTasksRemoteDataSource);
        }
        return INSTANCE;
    }
    @Override
    public void getTasks(@NonNull final LoadTasksCallBack callBack) {
            checkNotNull(callBack);
            if(mCaches!=null&&!mCacheIsDirty){
                callBack.onLoadTasks(new ArrayList<Task>(mCaches.values()));
                return;
            }

            if(mCacheIsDirty){
                getTasksDataFromRemote(callBack);
            }else{
                mTasksLocalDataSource.getTasks(new LoadTasksCallBack() {
                    @Override
                    public void onLoadTasks(List<Task> tasks) {
                        refreshCaches(tasks);
                        callBack.onLoadTasks(new ArrayList<Task>(mCaches.values()));
                    }

                    @Override
                    public void onDataNotAvailable() {
                        getTasksDataFromRemote(callBack);
                    }
                });
            }
    }

    private void getTasksDataFromRemote(final LoadTasksCallBack callBack) {
        mTasksRemoteDataSource.getTasks(new LoadTasksCallBack() {
            @Override
            public void onLoadTasks(List<Task> tasks) {
                refreshCaches(tasks);
                refreshLocalData(tasks);
                callBack.onLoadTasks(new ArrayList<Task>(mCaches.values()));
            }

            @Override
            public void onDataNotAvailable() {
                callBack.onDataNotAvailable();
            }
        });
    }

    private void refreshLocalData(List<Task> tasks) {
        mTasksLocalDataSource.deleteAllTasks();
        for (Task task : tasks) {
            mTasksLocalDataSource.saveTask(task);
        }
    }

    private void refreshCaches(List<Task> tasks) {
        if(mCaches ==null){
            mCaches = new LinkedHashMap<>();
        }

        mCaches.clear();
        for (Task task : tasks) {
            mCaches.put(task.getId(),task);
        }
    }

    @Override
    public void getTask(@NonNull final String taskId, @NonNull final GetTaskCallBack callBack) {
                //内存-》磁盘-》网络
        checkNotNull(taskId);
        checkNotNull(callBack);
        Task taskWithId = getTaskWithId(taskId);
        if(taskWithId!=null){
            callBack.onTaskLoaded(taskWithId);
            return;
        }

        mTasksLocalDataSource.getTask(taskId, new GetTaskCallBack() {
            @Override
            public void onTaskLoaded(Task task) {
                if(mCaches==null)mCaches=new LinkedHashMap<>();
                mCaches.put(task.getId(),task);
                callBack.onTaskLoaded(task);
            }

            @Override
            public void onDataNotAvailable() {
                    mTasksRemoteDataSource.getTask(taskId, new GetTaskCallBack() {
                        @Override
                        public void onTaskLoaded(Task task) {
                            mTasksLocalDataSource.saveTask(task);
                            if(mCaches==null)mCaches=new LinkedHashMap<>();
                            mCaches.put(task.getId(),task);
                            callBack.onTaskLoaded(task);
                        }

                        @Override
                        public void onDataNotAvailable() {
                            callBack.onDataNotAvailable();
                        }
                    });
            }
        });
    }

    private Task getTaskWithId(String taskId) {
        checkNotNull(taskId);
        if(mCaches==null||mCaches.isEmpty()) return null;
       return mCaches.get(taskId);
    }

    @Override
    public void saveTask(@NonNull Task task) {
        checkNotNull(task);
        mTasksRemoteDataSource.saveTask(task);
        mTasksLocalDataSource.saveTask(task);
        if(mCaches==null)mCaches=new LinkedHashMap<>();
        mCaches.put(task.getId(),task);
    }

    @Override
    public void completeTask(Task task) {
        checkNotNull(task);
        mTasksRemoteDataSource.completeTask(task);
        mTasksLocalDataSource.completeTask(task);

        Task newTask = new Task(task.getId(),task.getTitle(),task.getDescription(),true);
        if(mCaches==null||mCaches.isEmpty())mCaches=new LinkedHashMap<>();
        mCaches.put(task.getId(),newTask);
    }

    @Override
    public void completeTask(String taskid) {
        checkNotNull(taskid);
        completeTask(getTaskWithId(taskid));
    }

    @Override
    public void activeTask(Task task) {
        checkNotNull(task);
        mTasksRemoteDataSource.activeTask(task);
        mTasksLocalDataSource.activeTask(task);
        Task newTask = new Task(task.getId(),task.getTitle(),task.getDescription(),false);
        if(mCaches==null||mCaches.isEmpty())mCaches=new LinkedHashMap<>();
        mCaches.put(task.getId(),newTask);
    }

    @Override
    public void activeTask(String taskid) {
        checkNotNull(taskid);
        activeTask(getTaskWithId(taskid));
    }

    @Override
    public void deleteTask(String taskid) {
        checkNotNull(taskid);
        mTasksRemoteDataSource.deleteTask(taskid);
        mTasksLocalDataSource.deleteTask(taskid);
        if(mCaches==null||mCaches.isEmpty())mCaches=new LinkedHashMap<>();
        mCaches.remove(taskid);
    }

    @Override
    public void deleteAllTasks() {
        mTasksRemoteDataSource.deleteAllTasks();
        mTasksLocalDataSource.deleteAllTasks();
        if(mCaches!=null&&!mCaches.isEmpty())mCaches.clear();
    }

    @Override
    public void clearCompletedTasks() {
        mTasksRemoteDataSource.clearCompletedTasks();
        mTasksLocalDataSource.clearCompletedTasks();
        if(mCaches!=null&&!mCaches.isEmpty()){
            Iterator<Map.Entry<String, Task>> iterator = mCaches.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<String, Task> next = iterator.next();
                if(next.getValue().isCompleted())iterator.remove();
            }
        }
    }

    @Override
    public void refreshTasks() {
        mCacheIsDirty =true;
    }
}
