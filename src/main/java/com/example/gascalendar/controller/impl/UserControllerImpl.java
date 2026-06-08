package com.example.gascalendar.controller.impl;

import com.example.gascalendar.controller.UserController;
import com.example.gascalendar.dto.request.RegisterRequest;
import com.example.gascalendar.dto.request.UserUpdateRequest;
import com.example.gascalendar.dto.response.ApiResponse;
import com.example.gascalendar.dto.response.SuccessResponse;
import com.example.gascalendar.dto.response.UserResponse;
import com.example.gascalendar.entity.enums.UserRole;
import com.example.gascalendar.entity.enums.UserStatus;
import com.example.gascalendar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
public class UserControllerImpl implements UserController {
    @Autowired
    private UserService userService;
    @Override
    public ResponseEntity<ApiResponse<List<UserResponse>>> getUsers(UserRole role, UserStatus status, Integer page, Integer pageSize) {
        List<UserResponse> userResponseList = userService.getUsersByStatusAndRole(role, status);
        ApiResponse<List<UserResponse>> response = new ApiResponse<>();

        response.setData(userResponseList);
        response.setMessage("Users found");
        response.setSuccess(true);
        return ResponseEntity.ok(response);
    }



    @Override
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(String userId, UserUpdateRequest request) {
        ApiResponse<UserResponse> response = new ApiResponse<>();
        response.setSuccess(true);
        response.setMessage("Updated successfully");
        response.setData(userService.saveUser(userId, request));
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<SuccessResponse> deleteUser(String userId) {
        userService.deleteUser(userId);
        SuccessResponse response = new SuccessResponse();
        response.setMessage("User deleted successfully");
        response.setSuccess(true);
        return ResponseEntity.ok(response);
    }
}
