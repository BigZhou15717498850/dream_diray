package com.example.ly309313.demo_database.data.source;

import android.support.annotation.NonNull;

import com.example.ly309313.demo_database.tasks.domain.model.Task;

import java.util.List;

/**
 * 作者 LY309313
 * 日期 2018/5/12
 * 描述
 */

public interface TasksDataSource {

    interface LoadTasksCallBack{

        void onLoadTasks(List<Task> tasks);

        void onDataNotAvailable();
    }

    interface GetTaskCallBack{

        void onTaskLoaded(Task task);

        void onDataNotAvailable();
    }

    void getTasks(@NonNull LoadTasksCallBack callBack);

    void getTask(@NonNull String taskId,@NonNull GetTaskCallBack callBack);

    void saveTask(@NonNull Task task);

    void completeTask(Task task);

    void completeTask(String taskid);

    void activeTask(Task task);

    void activeTask(String taskid);

    void deleteTask(String taskid);

    void deleteAllTasks();

    void clearCompletedTasks();

    void refreshTasks();


}
