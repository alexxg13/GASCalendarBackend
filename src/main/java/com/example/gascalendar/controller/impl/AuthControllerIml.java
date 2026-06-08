package com.example.gascalendar.controller.impl;

import com.example.gascalendar.controller.AuthController;
import com.example.gascalendar.dto.request.LoginRequest;
import com.example.gascalendar.dto.request.RegisterRequest;
import com.example.gascalendar.dto.response.*;
import com.example.gascalendar.entity.User;
import com.example.gascalendar.mappers.UserMapper;
import com.example.gascalendar.service.UserService;
import com.example.gascalendar.entity.enums.SessionKeys;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Data
public class AuthControllerIml implements AuthController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity<LoginResponse> login(LoginRequest request, HttpSession session) {
        boolean exists = userService.existsUserByEmail(request.getEmail());
        LoginResponse loginResponse = new LoginResponse();
        if(exists){
            loginResponse.setSuccess(true);
            loginResponse.setMessage("Login successful");

            LoginDataResponse loginResponseData = new LoginDataResponse();
            UserResponse user = userMapper.userToUserResponse(userService.getUserByEmail(request.getEmail()));
            loginResponseData.setUser(user);
            loginResponse.setData(loginResponseData);

            session.setAttribute(SessionKeys.USER.toString(), user);
            return ResponseEntity.ok(loginResponse);
        }
        loginResponse.setSuccess(false);
        loginResponse.setMessage("Authentication required");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(loginResponse);
    }

    @Override
    public ResponseEntity<LoginResponse> register(RegisterRequest request, HttpSession session) {
        LoginResponse loginResponse = new LoginResponse();
        if (userService.registerUser(request)) {
            loginResponse.setSuccess(true);
            loginResponse.setMessage("User successfully registered");
            LoginDataResponse loginResponseData = new LoginDataResponse();
            UserResponse user = userMapper.userToUserResponse(userService.getUserByEmail(request.getEmail()));
            loginResponseData.setUser(user);
            loginResponse.setData(loginResponseData);
            session.setAttribute(SessionKeys.USER.toString(), user);
            return ResponseEntity.ok(loginResponse);
        }
        loginResponse.setSuccess(false);
        loginResponse.setMessage("User already exists");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(loginResponse);
    }

    @Override
    public ResponseEntity<SuccessResponse> logout(HttpSession session) {
        SuccessResponse successResponse = new SuccessResponse();
        session.invalidate();
        successResponse.setSuccess(true);
        successResponse.setMessage("Operation completed successfully");
        return ResponseEntity.ok(successResponse);
    }


    @Override
    public ResponseEntity<ApiResponse<UserResponse>> getCurrentUser(HttpSession session) {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        UserResponse user = (UserResponse) session.getAttribute(SessionKeys.USER.toString());

        apiResponse.setSuccess(true);
        apiResponse.setMessage("User successfully logged in");
        User userLastData = userService.getUserByID(user.getId());
        UserResponse currentUser = userMapper.userToUserResponse(userLastData);
        apiResponse.setData(currentUser);
        session.setAttribute(SessionKeys.USER.toString(), currentUser);

        return ResponseEntity.ok(apiResponse);
    }
}
