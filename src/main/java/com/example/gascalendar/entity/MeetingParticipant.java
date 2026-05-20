package com.example.gascalendar.entity;

import com.example.gascalendar.entity.enums.ConfirmationStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "meeting_participants")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeetingParticipant {

    @EmbeddedId
    private MeetingParticipantId id;

    @ManyToOne
    @MapsId("meetingId")
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "confirmation_status")
    private ConfirmationStatus confirmationStatus;
}