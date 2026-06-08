package com.example.gascalendar.entity;

import com.example.gascalendar.entity.enums.MeetingStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "meetings")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Meeting {

    @Id
    @Column(length = 36)
    @ColumnDefault("gen_random_uuid()::text")
    @Generated(sql = "gen_random_uuid()::text")
    private String id;

    @NotBlank
    private String title;

    @NotNull
    private LocalDate meetingDate;

    @NotBlank
    private String meetingTime;

    @Enumerated(EnumType.STRING)
    private MeetingStatus status;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @OneToMany(mappedBy = "meeting", cascade = CascadeType.ALL)
    private List<MeetingParticipant> participants;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
