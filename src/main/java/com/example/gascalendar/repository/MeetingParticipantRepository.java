package com.example.gascalendar.repository;

import com.example.gascalendar.entity.Meeting;
import com.example.gascalendar.entity.MeetingParticipant;
import com.example.gascalendar.entity.MeetingParticipantId;
import com.example.gascalendar.entity.User;
import com.example.gascalendar.entity.enums.ConfirmationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MeetingParticipantRepository extends JpaRepository<MeetingParticipant, MeetingParticipantId> {
    List<MeetingParticipant> findByMeeting(Meeting meeting);

    List<MeetingParticipant> findByMeeting_Id(String meetingId);

    List<MeetingParticipant> findById_MeetingId(String meetingId);

    List<MeetingParticipant> findByUser(User user);

    List<MeetingParticipant> findByUser_Id(String userId);

    List<MeetingParticipant> findById_UserId(String userId);

    Optional<MeetingParticipant> findByMeeting_IdAndUser_Id(String meetingId, String userId);

    List<MeetingParticipant> findByConfirmationStatus(ConfirmationStatus confirmationStatus);
}
