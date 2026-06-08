package com.example.gascalendar.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalendarItemsResponse {
    private List<TaskResponse> tasks;
    private List<MeetingResponse> meetings;
}
