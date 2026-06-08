package com.example.gascalendar.service.impl;

import com.example.gascalendar.dto.request.RegisterRequest;
import com.example.gascalendar.entity.User;
import com.example.gascalendar.entity.enums.UserStatus;
import com.example.gascalendar.repository.UserRepository;
import com.example.gascalendar.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserByID(String id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public boolean existsUserByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public boolean registerUser(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return false;
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .passwordHash(request.getPassword())
                .role(request.getRole())
                .status(UserStatus.ACTIVE)
                .build();

        userRepository.save(user);
        return true;
    }

    @Override
    @Transactional
    public void refreshLastActive(String id) {
        userRepository.findById(id)
                .ifPresent(user -> user.setLastActive(LocalDateTime.now()));
    }
}
