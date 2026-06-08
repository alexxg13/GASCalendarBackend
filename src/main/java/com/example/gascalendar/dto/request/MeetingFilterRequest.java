package com.example.gascalendar.dto.request;

import com.example.gascalendar.entity.enums.MeetingStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MeetingFilterRequest {
    private MeetingStatus status;
    private LocalDate startDate;
    private LocalDate endDate;
}
