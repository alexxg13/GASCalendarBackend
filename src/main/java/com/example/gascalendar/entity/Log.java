package com.example.gascalendar.entity;

import com.example.gascalendar.entity.enums.LogLevel;
import com.example.gascalendar.entity.enums.LogType;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "logs")
@Getter
@Setter
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    private LogLevel level;

    @Enumerated(EnumType.STRING)
    private LogType logType;

    private String ipAddress;

    @Column(columnDefinition = "TEXT")
    private String message;

    private String method;

    private String path;

    private Integer statusCode;
}