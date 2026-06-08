package com.example.gascalendar.service;

import com.example.gascalendar.dto.request.AvailabilityFilterRequest;
import com.example.gascalendar.dto.request.MeetingConfirmRequest;
import com.example.gascalendar.dto.request.MeetingCreateRequest;
import com.example.gascalendar.dto.request.MeetingFilterRequest;
import com.example.gascalendar.dto.response.CommonAvailabilityResponse;
import com.example.gascalendar.dto.response.MeetingResponse;
import com.example.gascalendar.dto.response.UserAvailabilityResponse;

import java.time.LocalDate;
import java.util.List;

public interface MeetingsService {
    List<MeetingResponse> getMeetings(String userId, MeetingFilterRequest filter);

    MeetingResponse createMeeting(String userId, MeetingCreateRequest request);

    void deleteMeeting(String userId, String meetingId);

    MeetingResponse confirmMeeting(String userId, String meetingId, MeetingConfirmRequest request);

    List<UserAvailabilityResponse> getUserAvailability(String userId, LocalDate startDate, LocalDate endDate);

    CommonAvailabilityResponse getCommonAvailability(String userId, AvailabilityFilterRequest request);

    List<MeetingResponse> getMeetingsByUserID(String userId);
}
