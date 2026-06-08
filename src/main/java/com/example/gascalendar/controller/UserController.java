package com.example.gascalendar.controller;

import com.example.gascalendar.dto.request.RegisterRequest;
import com.example.gascalendar.dto.request.UserUpdateRequest;
import com.example.gascalendar.dto.response.ApiResponse;
import com.example.gascalendar.dto.response.SuccessResponse;
import com.example.gascalendar.dto.response.UserResponse;
import com.example.gascalendar.entity.enums.UserRole;
import com.example.gascalendar.entity.enums.UserStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/api/admin/users")
@Tag(name = "Admin", description = "Administrative user management")
public interface UserController {
    @Operation(summary = "Get all users")
    @GetMapping
    ResponseEntity<ApiResponse<List<UserResponse>>> getUsers(
            @RequestParam(required = false) UserRole role,
            @RequestParam(required = false) UserStatus status,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer pageSize
    );

    @Operation(summary = "Update user")
    @PatchMapping("/{userId}")
    ResponseEntity<ApiResponse<UserResponse>> updateUser(
            @PathVariable String userId,
            @Valid @RequestBody UserUpdateRequest request
    );

    @Operation(summary = "Delete user")
    @DeleteMapping("/{userId}")
    ResponseEntity<SuccessResponse> deleteUser(@PathVariable String userId);
}
