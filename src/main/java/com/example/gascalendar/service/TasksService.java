package com.example.gascalendar.service;

import com.example.gascalendar.dto.request.TaskCreateRequest;
import com.example.gascalendar.dto.request.TaskMoveRequest;
import com.example.gascalendar.dto.request.TaskUpdateRequest;
import com.example.gascalendar.dto.response.ColumnResponse;
import com.example.gascalendar.dto.response.TaskResponse;

import java.util.List;

public interface TasksService {
    List<ColumnResponse> getColumns(String userId);

    TaskResponse createTask(String userId, TaskCreateRequest request);

    TaskResponse updateTask(String userId, String taskId, TaskUpdateRequest request);

    void deleteTask(String userId, String taskId);

    void moveTask(String userId, String taskId, TaskMoveRequest request);

    TaskResponse toggleTaskComplete(String userId, String taskId);
}
