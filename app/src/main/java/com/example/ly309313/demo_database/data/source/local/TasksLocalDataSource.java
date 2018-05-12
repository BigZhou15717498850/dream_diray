package com.example.ly309313.demo_database.data.source.local;

import android.support.annotation.NonNull;

import com.example.ly309313.demo_database.data.source.TasksDataSource;
import com.example.ly309313.demo_database.tasks.domain.model.Task;
import com.example.ly309313.demo_database.utils.AppExecutors;

import java.util.List;

/**
 * 作者 LY309313
 * 日期 2018/5/12
 * 描述
 */

public class TasksLocalDataSource implements TasksDataSource {

    private static TasksLocalDataSource INSTANCE;

    private TasksDao mTasksDao;

    private AppExecutors mAppExecutors;

    private TasksLocalDataSource(@NonNull TasksDao tasksDao,@NonNull AppExecutors appExecutors){
        this.mTasksDao = tasksDao;
        this.mAppExecutors = appExecutors;
    }

    public static TasksLocalDataSource getInstance(@NonNull TasksDao tasksDao,@NonNull AppExecutors appExecutors){
            if(INSTANCE ==null){
                synchronized (TasksLocalDataSource.class){
                    if(INSTANCE ==null){
                        INSTANCE = new TasksLocalDataSource(tasksDao,appExecutors);
                    }
                }
            }

            return INSTANCE;
    }

    @Override
    public void getTasks(@NonNull final LoadTasksCallBack callBack) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
              final List<Task> tasks = mTasksDao.getTasks();
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if(tasks==null||tasks.isEmpty()){
                            callBack.onDataNotAvailable();
                        }else{
                            callBack.onLoadTasks(tasks);
                        }
                    }
                });
            }
        };

        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void getTask(@NonNull final String taskId, @NonNull final GetTaskCallBack callBack) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    final Task task = mTasksDao.getTaskById(taskId);
                    mAppExecutors.mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            if(task==null){
                                callBack.onDataNotAvailable();
                            }else{
                                callBack.onTaskLoaded(task);
                            }
                        }
                    });
                }
            };

            mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void saveTask(@NonNull final Task task) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    mTasksDao.addTask(task);
                }
            };
            mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void completeTask(final Task task) {

    }

    @Override
    public void completeTask(final String taskid) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mTasksDao.updateTaskCompleted(taskid,true);
            }
        };
        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void activeTask(Task task) {

    }

    @Override
    public void activeTask(final String taskid) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mTasksDao.updateTaskCompleted(taskid,false);
            }
        };
        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void deleteTask(final String taskid) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    mTasksDao.deleteTaskById(taskid);
                }
            };
            mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void deleteAllTasks() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mTasksDao.deleteAllTasks();
            }
        };
        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void clearCompletedTasks() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mTasksDao.deleteCompletedTask();
            }
        };

        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void refreshTasks() {

    }

    public void destoryInstance(){
        INSTANCE =null;
    }
}
