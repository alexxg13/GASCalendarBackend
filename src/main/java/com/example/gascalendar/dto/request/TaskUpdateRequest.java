package com.example.gascalendar.dto.request;

import com.example.gascalendar.entity.enums.TaskPriority;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskUpdateRequest {
    @Size(min = 1, max = 200)
    private String title;

    @Size(max = 1000)
    private String description;

    private TaskPriority priority;

    private String assignee;

    private LocalDate date;

    @Pattern(regexp = "^\\d{2}:\\d{2}$")
    private String time;

    private Boolean completed;
}
