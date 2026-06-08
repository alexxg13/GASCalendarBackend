package com.example.gascalendar.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class MeetingCreateRequest {
    @NotBlank
    @Size(min = 1, max = 200)
    private String title;

    @NotNull
    private LocalDate date;

    @NotBlank
    @Pattern(regexp = "^\\d{2}:\\d{2}$")
    private String time;

    @NotEmpty
    private List<String> participants;
}
