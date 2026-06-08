package com.example.gascalendar.controller;

import com.example.gascalendar.dto.request.TaskCreateRequest;
import com.example.gascalendar.dto.request.TaskMoveRequest;
import com.example.gascalendar.dto.request.TaskUpdateRequest;
import com.example.gascalendar.dto.response.ApiResponse;
import com.example.gascalendar.dto.response.ColumnResponse;
import com.example.gascalendar.dto.response.SuccessResponse;
import com.example.gascalendar.dto.response.TaskResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api/tasks")
@Tag(name = "Tasks", description = "Kanban board task management")
public interface TasksController {
    @Operation(summary = "Get all tasks organized by columns")
    @GetMapping
    ResponseEntity<ApiResponse<List<ColumnResponse>>> getTasks(HttpSession session);

    @Operation(summary = "Create a new task")
    @PostMapping
    ResponseEntity<ApiResponse<TaskResponse>> createTask(@Valid @RequestBody TaskCreateRequest request,
                                                         HttpSession session);

    @Operation(summary = "Update a task")
    @PatchMapping("/{taskId}")
    ResponseEntity<ApiResponse<TaskResponse>> updateTask(@PathVariable String taskId,
                                                         @Valid @RequestBody TaskUpdateRequest request,
                                                         HttpSession session);

    @Operation(summary = "Delete a task")
    @DeleteMapping("/{taskId}")
    ResponseEntity<SuccessResponse> deleteTask(@PathVariable String taskId,
                                               HttpSession session);

    @Operation(summary = "Move task to different column")
    @PatchMapping("/{taskId}/move")
    ResponseEntity<SuccessResponse> moveTask(@PathVariable String taskId,
                                             @Valid @RequestBody TaskMoveRequest request,
                                             HttpSession session);

    @Operation(summary = "Toggle task completion status")
    @PatchMapping("/{taskId}/toggle-complete")
    ResponseEntity<ApiResponse<TaskResponse>> toggleTaskComplete(@PathVariable String taskId,
                                                                 HttpSession session);
}
