package com.example.gascalendar.controller.impl;

import com.example.gascalendar.controller.TasksController;
import com.example.gascalendar.dto.request.TaskCreateRequest;
import com.example.gascalendar.dto.request.TaskMoveRequest;
import com.example.gascalendar.dto.request.TaskUpdateRequest;
import com.example.gascalendar.dto.response.ApiResponse;
import com.example.gascalendar.dto.response.ColumnResponse;
import com.example.gascalendar.dto.response.SuccessResponse;
import com.example.gascalendar.dto.response.TaskResponse;
import com.example.gascalendar.dto.response.UserResponse;
import com.example.gascalendar.entity.enums.SessionKeys;
import com.example.gascalendar.service.TasksService;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@Data
public class TasksControllerIml implements TasksController {
    @Autowired
    private TasksService tasksService;


    @Override
    public ResponseEntity<ApiResponse<List<ColumnResponse>>> getTasks(HttpSession session) {
        ApiResponse<List<ColumnResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(true);
        apiResponse.setMessage("Success");
        apiResponse.setData(tasksService.getColumns(getUserId(session)));
        return ResponseEntity.ok(apiResponse);
    }

    @Override
    public ResponseEntity<ApiResponse<TaskResponse>> createTask(TaskCreateRequest request, HttpSession session) {
        ApiResponse<TaskResponse> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(true);
        apiResponse.setMessage("Task created");
        apiResponse.setData(tasksService.createTask(getUserId(session), request));
        return ResponseEntity.ok(apiResponse);
    }

    @Override
    public ResponseEntity<ApiResponse<TaskResponse>> updateTask(String taskId, TaskUpdateRequest request, HttpSession session) {
        ApiResponse<TaskResponse> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(true);
        apiResponse.setMessage("Task updated");
        apiResponse.setData(tasksService.updateTask(getUserId(session), taskId, request));
        return ResponseEntity.ok(apiResponse);
    }

    @Override
    public ResponseEntity<SuccessResponse> deleteTask(String taskId, HttpSession session) {
        tasksService.deleteTask(getUserId(session), taskId);
        SuccessResponse successResponse = new SuccessResponse();
        successResponse.setMessage("Deleted successfully");
        successResponse.setSuccess(true);
        return ResponseEntity.ok(successResponse);
    }

    @Override
    public ResponseEntity<SuccessResponse> moveTask(String taskId, TaskMoveRequest request, HttpSession session) {
        tasksService.moveTask(getUserId(session), taskId, request);
        SuccessResponse successResponse = new SuccessResponse();
        successResponse.setMessage("Moved successfully");
        successResponse.setSuccess(true);
        return ResponseEntity.ok(successResponse);
    }

    @Override
    public ResponseEntity<ApiResponse<TaskResponse>> toggleTaskComplete(String taskId, HttpSession session) {
        ApiResponse<TaskResponse> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(true);
        apiResponse.setMessage("Toggle completed");
        apiResponse.setData(tasksService.toggleTaskComplete(getUserId(session), taskId));
        return ResponseEntity.ok(apiResponse);
    }

    private String getUserId(HttpSession session) {
        UserResponse user = (UserResponse) session.getAttribute(SessionKeys.USER.toString());
        return user.getId();
    }
}
