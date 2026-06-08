package com.example.gascalendar.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class AvailabilityFilterRequest {
    @NotEmpty
    private List<String> userIds;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;
}
