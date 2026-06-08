package com.example.gascalendar.dto.request;

import com.example.gascalendar.entity.enums.ConfirmationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MeetingConfirmRequest {
    @NotNull
    private ConfirmationStatus status;
}
