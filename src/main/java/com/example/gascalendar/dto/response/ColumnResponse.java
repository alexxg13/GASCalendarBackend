package com.example.gascalendar.dto.response;

import com.example.gascalendar.entity.enums.TaskColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ColumnResponse {
    private TaskColumn id;
    private String title;
    private List<TaskResponse> tasks;
    private Integer order;
}
