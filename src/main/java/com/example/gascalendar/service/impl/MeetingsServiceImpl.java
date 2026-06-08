package com.example.gascalendar.service.impl;

import com.example.gascalendar.dto.request.AvailabilityFilterRequest;
import com.example.gascalendar.dto.request.MeetingConfirmRequest;
import com.example.gascalendar.dto.request.MeetingCreateRequest;
import com.example.gascalendar.dto.request.MeetingFilterRequest;
import com.example.gascalendar.dto.response.CommonAvailabilityResponse;
import com.example.gascalendar.dto.response.MeetingResponse;
import com.example.gascalendar.dto.response.UserAvailabilityResponse;
import com.example.gascalendar.entity.Meeting;
import com.example.gascalendar.entity.MeetingParticipant;
import com.example.gascalendar.entity.MeetingParticipantId;
import com.example.gascalendar.entity.User;
import com.example.gascalendar.entity.enums.ConfirmationStatus;
import com.example.gascalendar.entity.enums.MeetingStatus;
import com.example.gascalendar.exeptions.InvalidDateRangeException;
import com.example.gascalendar.exeptions.MeetingNotFoundException;
import com.example.gascalendar.exeptions.MeetingParticipantNotFoundException;
import com.example.gascalendar.exeptions.NotFoundUserException;
import com.example.gascalendar.mappers.MeetingsMapper;
import com.example.gascalendar.repository.MeetingParticipantRepository;
import com.example.gascalendar.repository.MeetingRepository;
import com.example.gascalendar.repository.UserRepository;
import com.example.gascalendar.service.MeetingsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class MeetingsServiceImpl implements MeetingsService {
    private static final List<String> WORKING_SLOTS = IntStream.rangeClosed(9, 17)
            .mapToObj(hour -> LocalTime.of(hour, 0).toString())
            .toList();

    private final MeetingRepository meetingRepository;
    private final MeetingParticipantRepository meetingParticipantRepository;
    private final UserRepository userRepository;
    private final MeetingsMapper meetingsMapper;

    public MeetingsServiceImpl(
            MeetingRepository meetingRepository,
            MeetingParticipantRepository meetingParticipantRepository,
            UserRepository userRepository,
            MeetingsMapper meetingsMapper
    ) {
        this.meetingRepository = meetingRepository;
        this.meetingParticipantRepository = meetingParticipantRepository;
        this.userRepository = userRepository;
        this.meetingsMapper = meetingsMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MeetingResponse> getMeetings(String userId, MeetingFilterRequest filter) {
        getUser(userId);
        validateDateRange(filter.getStartDate(), filter.getEndDate());

        List<Meeting> accessibleMeetings = new ArrayList<>(meetingRepository.findByCreatedBy_Id(userId));
        accessibleMeetings.addAll(
                meetingParticipantRepository.findByUser_Id(userId).stream()
                        .map(MeetingParticipant::getMeeting)
                        .toList()
        );

        return accessibleMeetings.stream()
                .filter(meeting -> matchesFilter(meeting, filter))
                .collect(Collectors.toMap(
                        Meeting::getId,
                        meeting -> meeting,
                        (first, second) -> first,
                        LinkedHashMap::new
                ))
                .values()
                .stream()
                .sorted(Comparator.comparing(Meeting::getMeetingDate)
                        .thenComparing(Meeting::getMeetingTime))
                .map(meetingsMapper::toMeetingResponse)
                .toList();
    }

    @Override
    @Transactional
    public MeetingResponse createMeeting(String userId, MeetingCreateRequest request) {
        User creator = getUser(userId);

        Meeting meeting = Meeting.builder()
                .title(request.getTitle())
                .meetingDate(request.getDate())
                .meetingTime(request.getTime())
                .status(MeetingStatus.PENDING)
                .createdBy(creator)
                .participants(new ArrayList<>())
                .build();

        meeting = meetingRepository.save(meeting);

        LinkedHashSet<String> participantIds = new LinkedHashSet<>(request.getParticipants());
        participantIds.remove(userId);

        for (String participantId : participantIds) {
            User participantUser = getUser(participantId);

            MeetingParticipant participant = MeetingParticipant.builder()
                    .id(new MeetingParticipantId(meeting.getId(), participantId))
                    .meeting(meeting)
                    .user(participantUser)
                    .confirmationStatus(ConfirmationStatus.PENDING)
                    .build();

            meeting.getParticipants().add(participant);
        }

        meetingParticipantRepository.saveAll(meeting.getParticipants());
        return meetingsMapper.toMeetingResponse(meeting);
    }

    @Override
    @Transactional
    public void deleteMeeting(String userId, String meetingId) {
        Meeting meeting = meetingRepository.findByIdAndCreatedBy_Id(meetingId, userId)
                .orElseThrow(() -> new MeetingNotFoundException(meetingId));

        meetingRepository.delete(meeting);
    }

    @Override
    @Transactional
    public MeetingResponse confirmMeeting(String userId, String meetingId, MeetingConfirmRequest request) {
        MeetingParticipant currentParticipant = meetingParticipantRepository
                .findByMeeting_IdAndUser_Id(meetingId, userId)
                .orElseThrow(() -> new MeetingParticipantNotFoundException(userId, meetingId));

        currentParticipant.setConfirmationStatus(request.getStatus());

        Meeting meeting = currentParticipant.getMeeting();
        List<MeetingParticipant> participants = meetingParticipantRepository.findByMeeting_Id(meetingId);

        boolean allAccepted = !participants.isEmpty()
                && participants.stream()
                .allMatch(participant ->
                        participant.getConfirmationStatus() == ConfirmationStatus.ACCEPTED
                );

        meeting.setStatus(allAccepted ? MeetingStatus.CONFIRMED : MeetingStatus.PENDING);
        meeting.setParticipants(participants);

        return meetingsMapper.toMeetingResponse(meeting);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserAvailabilityResponse> getUserAvailability(String userId, LocalDate startDate, LocalDate endDate) {
        getUser(userId);
        LocalDate[] range = normalizeDateRange(startDate, endDate);
        List<User> users = userRepository.findAll();
        List<Meeting> meetings = meetingRepository
                .findByMeetingDateBetweenOrderByMeetingDateAscMeetingTimeAsc(range[0], range[1]);

        return users.stream()
                .map(user -> new UserAvailabilityResponse(
                        user.getId(),
                        user.getName(),
                        null,
                        buildAvailability(user.getId(), range[0], range[1], meetings)
                ))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CommonAvailabilityResponse getCommonAvailability(String userId, AvailabilityFilterRequest request) {
        getUser(userId);
        validateDateRange(request.getStartDate(), request.getEndDate());

        List<String> participantIds = new ArrayList<>(new LinkedHashSet<>(request.getUserIds()));
        participantIds.forEach(this::getUser);

        List<Meeting> meetings = meetingRepository.findByMeetingDateBetweenOrderByMeetingDateAscMeetingTimeAsc(
                request.getStartDate(),
                request.getEndDate()
        );

        Map<String, List<String>> commonAvailability = new LinkedHashMap<>();
        for (LocalDate date = request.getStartDate(); !date.isAfter(request.getEndDate()); date = date.plusDays(1)) {
            LocalDate currentDate = date;
            List<String> commonSlots = WORKING_SLOTS.stream()
                    .filter(slot -> participantIds.stream().allMatch(participantId ->
                            isSlotAvailable(participantId, currentDate, slot, meetings)
                    ))
                    .toList();

            commonAvailability.put(currentDate.toString(), commonSlots);
        }

        return new CommonAvailabilityResponse(participantIds, commonAvailability);
    }

    private User getUser(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundUserException(userId));
    }

    private boolean matchesFilter(Meeting meeting, MeetingFilterRequest filter) {
        return (filter.getStatus() == null || meeting.getStatus() == filter.getStatus())
                && (filter.getStartDate() == null || !meeting.getMeetingDate().isBefore(filter.getStartDate()))
                && (filter.getEndDate() == null || !meeting.getMeetingDate().isAfter(filter.getEndDate()));
    }

    private LocalDate[] normalizeDateRange(LocalDate startDate, LocalDate endDate) {
        LocalDate normalizedStart = startDate == null ? LocalDate.now() : startDate;
        LocalDate normalizedEnd = endDate == null ? normalizedStart.plusDays(7) : endDate;
        validateDateRange(normalizedStart, normalizedEnd);
        return new LocalDate[]{normalizedStart, normalizedEnd};
    }

    private void validateDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new InvalidDateRangeException();
        }
    }

    private Map<String, List<String>> buildAvailability(
            String userId,
            LocalDate startDate,
            LocalDate endDate,
            List<Meeting> meetings
    ) {
        Map<String, List<String>> availability = new LinkedHashMap<>();

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            LocalDate currentDate = date;
            List<String> freeSlots = WORKING_SLOTS.stream()
                    .filter(slot -> isSlotAvailable(userId, currentDate, slot, meetings))
                    .toList();
            availability.put(currentDate.toString(), freeSlots);
        }

        return availability;
    }

    private boolean isSlotAvailable(
            String userId,
            LocalDate date,
            String slot,
            List<Meeting> meetings
    ) {
        return meetings.stream()
                .filter(meeting -> meeting.getStatus() != MeetingStatus.CANCELLED)
                .filter(meeting -> meeting.getMeetingDate().equals(date))
                .filter(meeting -> meeting.getMeetingTime().equals(slot))
                .noneMatch(meeting -> isUserBusyAtMeeting(userId, meeting));
    }

    private boolean isUserBusyAtMeeting(String userId, Meeting meeting) {
        if (meeting.getCreatedBy().getId().equals(userId)) {
            return true;
        }

        return meeting.getParticipants().stream()
                .anyMatch(participant ->
                        participant.getUser().getId().equals(userId)
                                && participant.getConfirmationStatus() != ConfirmationStatus.DECLINED
                );
    }
}
