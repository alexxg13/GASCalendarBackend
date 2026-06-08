package com.example.gascalendar.controller;

import com.example.gascalendar.dto.request.MeetingConfirmRequest;
import com.example.gascalendar.dto.request.MeetingCreateRequest;
import com.example.gascalendar.dto.request.AvailabilityFilterRequest;
import com.example.gascalendar.dto.response.ApiResponse;
import com.example.gascalendar.dto.response.CommonAvailabilityResponse;
import com.example.gascalendar.dto.response.MeetingResponse;
import com.example.gascalendar.dto.response.SuccessResponse;
import com.example.gascalendar.dto.response.UserAvailabilityResponse;
import com.example.gascalendar.entity.enums.MeetingStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/api/meetings")
@Tag(name = "Meetings", description = "Meeting scheduling and availability")
public interface MeetingsController {
    @Operation(summary = "Get all meetings")
    @GetMapping
    ResponseEntity<ApiResponse<List<MeetingResponse>>> getMeetings(
            @RequestParam(required = false) MeetingStatus status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            HttpSession session
    );

    @Operation(summary = "Create a new meeting")
    @PostMapping
    ResponseEntity<ApiResponse<MeetingResponse>> createMeeting(@Valid @RequestBody MeetingCreateRequest request,
                                                               HttpSession session);

    @Operation(summary = "Cancel a meeting")
    @DeleteMapping("/{meetingId}")
    ResponseEntity<SuccessResponse> deleteMeeting(@PathVariable String meetingId,
                                                  HttpSession session);

    @Operation(summary = "Confirm or decline meeting")
    @PostMapping("/{meetingId}/confirm")
    ResponseEntity<ApiResponse<MeetingResponse>> confirmMeeting(@PathVariable String meetingId,
                                                                @Valid @RequestBody MeetingConfirmRequest request,
                                                                HttpSession session);

    @Operation(summary = "Get user availability")
    @GetMapping("/availability")
    ResponseEntity<ApiResponse<List<UserAvailabilityResponse>>> getUserAvailability(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            HttpSession session
    );

    @Operation(summary = "Find common availability for selected users")
    @PostMapping("/availability/common")
    ResponseEntity<ApiResponse<CommonAvailabilityResponse>> getCommonAvailability(
            @Valid @RequestBody AvailabilityFilterRequest request,
            HttpSession session
    );
}
