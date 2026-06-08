package com.example.gascalendar.mappers;

import com.example.gascalendar.dto.response.MeetingResponse;
import com.example.gascalendar.entity.Meeting;
import com.example.gascalendar.entity.MeetingParticipant;
import com.example.gascalendar.entity.enums.ConfirmationStatus;
import org.mapstruct.Mapper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring")
public interface MeetingsMapper {
    default MeetingResponse toMeetingResponse(Meeting meeting) {
        List<MeetingParticipant> meetingParticipants = meeting.getParticipants() == null
                ? List.of()
                : meeting.getParticipants();

        List<String> participantIds = meetingParticipants.stream()
                .map(participant -> participant.getUser().getId())
                .toList();

        Map<String, ConfirmationStatus> confirmations = new LinkedHashMap<>();
        meetingParticipants.forEach(participant -> confirmations.put(
                participant.getUser().getId(),
                participant.getConfirmationStatus()
        ));

        return new MeetingResponse(
                meeting.getId(),
                meeting.getTitle(),
                meeting.getMeetingDate(),
                meeting.getMeetingTime(),
                participantIds,
                confirmations,
                meeting.getStatus(),
                meeting.getCreatedBy().getId(),
                meeting.getCreatedAt(),
                meeting.getUpdatedAt()
        );
    }
}
