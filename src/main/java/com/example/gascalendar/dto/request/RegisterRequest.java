package com.example.gascalendar.dto.request;

import com.example.gascalendar.entity.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @Size(min = 2, max = 100)
    @NotBlank(message = "Name must be required")
    private String name;

    @Email(message = "Email must be valid")
    @NotBlank(message = "Email must be required")
    private String email;

    @Size(min = 6)
    @NotBlank(message = "Password must be required")
    private String password;

    private UserRole role = UserRole.VIEWER;
}
