package com.example.gascalendar.controller;

import com.example.gascalendar.dto.request.LoginRequest;
import com.example.gascalendar.dto.request.RegisterRequest;
import com.example.gascalendar.dto.response.ApiResponse;
import com.example.gascalendar.dto.response.ErrorResponse;
import com.example.gascalendar.dto.response.LoginResponse;
import com.example.gascalendar.dto.response.SuccessResponse;
import com.example.gascalendar.dto.response.TokenRefreshResponse;
import com.example.gascalendar.dto.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "User authentication and session management")
public interface AuthController {
    @Operation(summary = "User login")
    @PostMapping("/login")
    ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request,
                                        HttpSession session);

    @Operation(summary = "User registration")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Registration successful"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Invalid request",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "409",
                    description = "Email already exists"
            )
    })
    @PostMapping("/register")
    ResponseEntity<LoginResponse> register(@Valid @RequestBody RegisterRequest request,
                                           HttpSession session);

    @Operation(summary = "User logout")
    @PostMapping("/logout")
    ResponseEntity<SuccessResponse> logout(HttpSession session);


    @Operation(summary = "Get current user")
    @GetMapping("/me")
    ResponseEntity<ApiResponse<UserResponse>> getCurrentUser(HttpSession session);
}
