package com.example.gascalendar.dto.request;

import com.example.gascalendar.entity.enums.MeetingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class MeetingFilterRequest {
    private MeetingStatus status;
    private LocalDate startDate;
    private LocalDate endDate;
}
