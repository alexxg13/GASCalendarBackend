package com.example.gascalendar.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private Boolean success;
    private String message;
    private String error;
    private List<String> details;
    private LocalDateTime timestamp;
}
