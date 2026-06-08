package com.example.gascalendar.dto.request;

import com.example.gascalendar.entity.enums.TaskColumn;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TaskMoveRequest {
    @NotNull
    private TaskColumn toColumnId;
}
