package com.example.gascalendar.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MeetingParticipantId implements Serializable {
    private String meetingId;
    private String userId;
}