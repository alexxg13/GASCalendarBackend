package com.example.gascalendar.dto.request;

import com.example.gascalendar.entity.enums.UserRole;
import com.example.gascalendar.entity.enums.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserUpdateRequest {
    @Size(min = 2, max = 100)
    private String name;

    @Email
    private String email;

    @Size(min = 6)
    private String password;

    private UserRole role;

    private UserStatus status;
}
