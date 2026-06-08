package com.example.gascalendar.controller.impl;

import com.example.gascalendar.controller.CalendarController;
import com.example.gascalendar.dto.response.*;
import com.example.gascalendar.entity.User;
import com.example.gascalendar.entity.enums.SessionKeys;
import com.example.gascalendar.service.MeetingsService;
import com.example.gascalendar.service.TasksService;
import com.example.gascalendar.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.mapstruct.control.MappingControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@RestController
public class CalendarControllerImpl implements CalendarController {
    @Autowired
    private MeetingsService meetingsService;
    @Autowired
    private TasksService tasksService;


    @Override
    public ResponseEntity<ApiResponse<CalendarItemsResponse>> getCalendarItems(LocalDate startDate, LocalDate endDate, HttpSession session) {
        CalendarItemsResponse calendarItemsResponse = new CalendarItemsResponse();

        List<TaskResponse> taskResponses = tasksService.getTasks(getUserId(session));
        List<MeetingResponse> meetingResponses = meetingsService.getMeetingsByUserID(getUserId(session));

        calendarItemsResponse.setTasks(taskResponses);
        calendarItemsResponse.setMeetings(meetingResponses);

        ApiResponse<CalendarItemsResponse> apiResponse = new ApiResponse<>();
        apiResponse.setData(calendarItemsResponse);
        apiResponse.setMessage("Calendar Items Successfully Loaded");
        apiResponse.setSuccess(true);

        return ResponseEntity.ok(apiResponse);
    }


    private String getUserId(HttpSession session) {
        UserResponse user = (UserResponse) session.getAttribute(SessionKeys.USER.toString());
        return user.getId();
    }
}
