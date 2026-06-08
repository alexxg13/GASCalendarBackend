package com.example.gascalendar.service;

import com.example.gascalendar.dto.request.RegisterRequest;
import com.example.gascalendar.entity.User;

public interface UserService {
    User getUserByID(String id);
    boolean existsUserByEmail(String email);
    User getUserByEmail(String email);
    boolean registerUser(RegisterRequest user);
    void refreshLastActive(String id);

}
