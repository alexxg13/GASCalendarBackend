package com.example.gascalendar.entity;

import com.example.gascalendar.entity.enums.UserRole;
import com.example.gascalendar.entity.enums.UserStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "User entity")
public class User {

    @Id
    @Column(length = 36)
    @Schema(example = "uuid")
    private String id;

    @NotBlank
    @Size(max = 100)
    private String name;

    @Email
    @NotBlank
    @Column(unique = true)
    private String email;

    @NotBlank
    @Column(name = "password_hash")
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @NotNull
    private UserRole role;

    @Enumerated(EnumType.STRING)
    @NotNull
    private UserStatus status;

    private LocalDateTime lastActive;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Task> tasks;

    @OneToMany(mappedBy = "createdBy")
    private List<Meeting> meetingsCreated;

    @OneToMany(mappedBy = "user")
    private List<Session> sessions;
}