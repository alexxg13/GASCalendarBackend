package com.example.gascalendar.entity;

import com.example.gascalendar.entity.enums.IpRuleType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "ip_rules")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IpRule {

    @Id
    private String id;

    @NotBlank
    @Column(name = "ip_address")
    private String ipAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "rule_type")
    private IpRuleType ruleType;

    private String reason;

    private Integer requestCount = 0;

    @CreationTimestamp
    private LocalDateTime addedDate;
}