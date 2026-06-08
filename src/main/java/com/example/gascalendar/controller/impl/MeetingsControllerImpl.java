package com.example.gascalendar.controller.impl;

import com.example.gascalendar.controller.MeetingsController;
import com.example.gascalendar.dto.request.AvailabilityFilterRequest;
import com.example.gascalendar.dto.request.MeetingConfirmRequest;
import com.example.gascalendar.dto.request.MeetingCreateRequest;
import com.example.gascalendar.dto.request.MeetingFilterRequest;
import com.example.gascalendar.dto.response.*;
import com.example.gascalendar.entity.enums.MeetingStatus;
import com.example.gascalendar.entity.enums.SessionKeys;
import com.example.gascalendar.service.MeetingsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public class MeetingsControllerImpl implements MeetingsController {
    private final MeetingsService meetingsService;

    public MeetingsControllerImpl(MeetingsService meetingsService) {
        this.meetingsService = meetingsService;
    }

    @Override
    public ResponseEntity<ApiResponse<List<MeetingResponse>>> getMeetings(MeetingStatus status, LocalDate startDate, LocalDate endDate, HttpSession session) {
        MeetingFilterRequest meetingFilterRequest = new MeetingFilterRequest(status, startDate, endDate);
        List<MeetingResponse> meetingResponses = meetingsService.getMeetings(getUserId(session), meetingFilterRequest);

        ApiResponse<List<MeetingResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setData(meetingResponses);
        apiResponse.setMessage("Meetings found");
        apiResponse.setSuccess(true);
        return ResponseEntity.ok(apiResponse);
    }

    @Override
    public ResponseEntity<ApiResponse<MeetingResponse>> createMeeting(MeetingCreateRequest request, HttpSession session) {
        MeetingResponse meeting = meetingsService.createMeeting(getUserId(session), request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Meeting created", meeting));
    }

    @Override
    public ResponseEntity<SuccessResponse> deleteMeeting(String meetingId, HttpSession session) {
        meetingsService.deleteMeeting(getUserId(session), meetingId);
        return ResponseEntity.ok(new SuccessResponse(true, "Meeting deleted"));
    }

    @Override
    public ResponseEntity<ApiResponse<MeetingResponse>> confirmMeeting(String meetingId, MeetingConfirmRequest request, HttpSession session) {
        MeetingResponse meeting = meetingsService.confirmMeeting(getUserId(session), meetingId, request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Meeting confirmation updated", meeting));
    }

    @Override
    public ResponseEntity<ApiResponse<List<UserAvailabilityResponse>>> getUserAvailability(LocalDate startDate, LocalDate endDate, HttpSession session) {
        List<UserAvailabilityResponse> availability = meetingsService.getUserAvailability(getUserId(session), startDate, endDate);
        return ResponseEntity.ok(new ApiResponse<>(true, "User availability found", availability));
    }

    @Override
    public ResponseEntity<ApiResponse<CommonAvailabilityResponse>> getCommonAvailability(AvailabilityFilterRequest request, HttpSession session) {
        CommonAvailabilityResponse availability = meetingsService.getCommonAvailability(getUserId(session), request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Common availability found", availability));
    }

    private String getUserId(HttpSession session) {
        UserResponse user = (UserResponse) session.getAttribute(SessionKeys.USER.toString());
        return user.getId();
    }
}
