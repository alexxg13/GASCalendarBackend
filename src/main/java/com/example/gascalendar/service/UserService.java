package com.example.gascalendar.service;

import com.example.gascalendar.dto.request.RegisterRequest;
import com.example.gascalendar.dto.request.UserUpdateRequest;
import com.example.gascalendar.dto.response.UserResponse;
import com.example.gascalendar.entity.User;
import com.example.gascalendar.entity.enums.UserRole;
import com.example.gascalendar.entity.enums.UserStatus;

import java.util.List;

public interface UserService {
    User getUserByID(String id);
    boolean existsUserByEmail(String email);
    User getUserByEmail(String email);
    boolean registerUser(RegisterRequest user);
    void refreshLastActive(String id);
    List<UserResponse> getUsersByStatusAndRole(UserRole role, UserStatus status);
    boolean login(String email, String password);
    UserResponse saveUser(String userId, UserUpdateRequest userUpdateRequest);
    void deleteUser(String id);

}
