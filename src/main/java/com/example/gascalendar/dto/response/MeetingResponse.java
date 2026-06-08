package com.example.gascalendar.dto.response;

import com.example.gascalendar.entity.enums.ConfirmationStatus;
import com.example.gascalendar.entity.enums.MeetingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeetingResponse {
    private String id;
    private String title;
    private LocalDate date;
    private String time;
    private List<String> participants;
    private Map<String, ConfirmationStatus> confirmations;
    private MeetingStatus status;
    private String createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
