package com.example.gascalendar.dto.request;

import com.example.gascalendar.entity.enums.TaskColumn;
import com.example.gascalendar.entity.enums.TaskPriority;
import com.example.gascalendar.entity.enums.TaskType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskCreateRequest {
    @NotBlank
    @Size(min = 1, max = 200)
    private String title;

    @Size(max = 1000)
    private String description;

    @NotNull
    private TaskPriority priority;

    @NotBlank
    private String assignee;

    @NotNull
    private TaskColumn columnId;

    private LocalDate date;

    @Pattern(regexp = "^\\d{2}:\\d{2}$")
    private String time;

    private TaskType type;
}
