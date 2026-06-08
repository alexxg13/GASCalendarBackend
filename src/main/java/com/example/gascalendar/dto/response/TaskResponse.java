package com.example.gascalendar.dto.response;

import com.example.gascalendar.entity.enums.TaskColumn;
import com.example.gascalendar.entity.enums.TaskPriority;
import com.example.gascalendar.entity.enums.TaskType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponse {
    private String id;
    private String title;
    private String description;
    private TaskPriority priority;
    private String assignee;
    private TaskColumn column;
    private LocalDate taskDate;
    private String taskTime;
    private TaskType taskType;
    private Boolean completed;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
