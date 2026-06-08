package com.example.gascalendar.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AvailabilityFilterRequest {
    private LocalDate startDate;
    private LocalDate endDate;
}
