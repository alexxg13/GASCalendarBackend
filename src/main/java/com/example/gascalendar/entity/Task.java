package com.example.gascalendar.entity;

import com.example.gascalendar.entity.enums.TaskColumn;
import com.example.gascalendar.entity.enums.TaskPriority;
import com.example.gascalendar.entity.enums.TaskType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @Column(length = 36)
    @ColumnDefault("gen_random_uuid()::text")
    @Generated(sql = "gen_random_uuid()::text")
    private String id;

    @NotBlank
    @Size(max = 200)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TaskPriority priority;

    @NotBlank
    private String assignee;

    @Enumerated(EnumType.STRING)
    @Column(name = "column_id")
    private TaskColumn column;

    private LocalDate taskDate;

    @Size(max = 5)
    private String taskTime;

    @Enumerated(EnumType.STRING)
    private TaskType taskType;

    private Boolean completed = false;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
