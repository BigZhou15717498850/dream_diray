package com.example.ly309313.demo_database.tasks.domain.filter;

import com.example.ly309313.demo_database.tasks.domain.model.Task;

import java.util.List;

/**
 * 作者 LY309313
 * 日期 2018/5/12
 * 描述
 */

public interface TasksFilter {

    List<Task> filter(List<Task> tasks);
}
