package com.example.gascalendar.repository;

import com.example.gascalendar.entity.Meeting;
import com.example.gascalendar.entity.User;
import com.example.gascalendar.entity.enums.MeetingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MeetingRepository extends JpaRepository<Meeting, String> {
    List<Meeting> findByCreatedBy(User createdBy);

    List<Meeting> findByCreatedBy_Id(String createdById);

    List<Meeting> findByStatus(MeetingStatus status);

    List<Meeting> findByMeetingDate(LocalDate meetingDate);

    List<Meeting> findByMeetingDateBetween(LocalDate startDate, LocalDate endDate);

    List<Meeting> findByStatusAndMeetingDateBetween(MeetingStatus status, LocalDate startDate, LocalDate endDate);

    List<Meeting> findByMeetingDateBetweenOrderByMeetingDateAscMeetingTimeAsc(LocalDate startDate, LocalDate endDate);

    Optional<Meeting> findByIdAndCreatedBy_Id(String meetingId, String userId);
}
