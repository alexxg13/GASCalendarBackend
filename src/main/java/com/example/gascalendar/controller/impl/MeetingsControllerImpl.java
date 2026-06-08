package com.example.gascalendar.controller.impl;

import com.example.gascalendar.controller.MeetingsController;
import com.example.gascalendar.dto.request.MeetingConfirmRequest;
import com.example.gascalendar.dto.request.MeetingCreateRequest;
import com.example.gascalendar.dto.response.ApiResponse;
import com.example.gascalendar.dto.response.MeetingResponse;
import com.example.gascalendar.dto.response.SuccessResponse;
import com.example.gascalendar.dto.response.UserAvailabilityResponse;
import com.example.gascalendar.entity.enums.MeetingStatus;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

public class MeetingsControllerImpl implements MeetingsController {
    @Override
    public ResponseEntity<ApiResponse<List<MeetingResponse>>> getMeetings(MeetingStatus status, LocalDate startDate, LocalDate endDate, HttpSession session) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse<MeetingResponse>> createMeeting(MeetingCreateRequest request, HttpSession session) {
        return null;
    }

    @Override
    public ResponseEntity<SuccessResponse> deleteMeeting(String meetingId, HttpSession session) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse<MeetingResponse>> confirmMeeting(String meetingId, MeetingConfirmRequest request, HttpSession session) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse<List<UserAvailabilityResponse>>> getUserAvailability(LocalDate startDate, LocalDate endDate, HttpSession session) {
        return null;
    }
}
