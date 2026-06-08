package com.example.gascalendar.service.impl;

import com.example.gascalendar.dto.request.RegisterRequest;
import com.example.gascalendar.dto.request.UserUpdateRequest;
import com.example.gascalendar.dto.response.UserResponse;
import com.example.gascalendar.entity.User;
import com.example.gascalendar.entity.enums.UserRole;
import com.example.gascalendar.entity.enums.UserStatus;
import com.example.gascalendar.mappers.UserMapper;
import com.example.gascalendar.repository.MeetingParticipantRepository;
import com.example.gascalendar.repository.MeetingRepository;
import com.example.gascalendar.repository.TaskRepository;
import com.example.gascalendar.repository.UserRepository;
import com.example.gascalendar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MeetingRepository meetingRepository;
    @Autowired
    private MeetingParticipantRepository meetingParticipantRepository;
    @Autowired
    private TaskRepository taskRepository;


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
        String passwordHash = passwordEncoder.encode(request.getPassword());
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .passwordHash(passwordHash)
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

    @Override
    public List<UserResponse> getUsersByStatusAndRole(UserRole role, UserStatus status) {
        if (role != null && status != null) {
            return userRepository.findByRoleAndStatus(role, status).stream()
                    .map(userMapper::userToUserResponse)
                    .toList();
        } else if (role != null) {
            return userRepository.findByRole(role).stream()
                    .map(userMapper::userToUserResponse)
                    .toList();
        } else if (status != null) {
            return userRepository.findByStatus(status).stream()
                    .map(userMapper::userToUserResponse)
                    .toList();
        }
        return userRepository.findAll().stream()
                .map(userMapper::userToUserResponse)
                .toList();
    }

    @Override
    public boolean login(String email, String password) {
        return userRepository.findByEmail(email)
                .map(user -> passwordEncoder.matches(password, user.getPasswordHash()))
                .orElse(false);
    }

    @Override
    @Transactional
    public UserResponse saveUser(String userId, UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return null;
        }
        if (userUpdateRequest.getName() != null) {
            user.setName(userUpdateRequest.getName());
        }
        if (userUpdateRequest.getEmail() != null) {
            user.setEmail(userUpdateRequest.getEmail());
        }
        if (userUpdateRequest.getPassword() != null && !userUpdateRequest.getPassword().isBlank()) {
            user.setPasswordHash(passwordEncoder.encode(userUpdateRequest.getPassword()));
        }
        if (userUpdateRequest.getRole() != null) {
            user.setRole(userUpdateRequest.getRole());
        }
        if (userUpdateRequest.getStatus() != null) {
            user.setStatus(userUpdateRequest.getStatus());
        }
        return userMapper.userToUserResponse(userRepository.save(user));
    }

    @Override
    @Transactional
    public void deleteUser(String id) {
        meetingRepository.deleteAll(meetingRepository.findByCreatedBy_Id(id));
        meetingRepository.flush();

        meetingParticipantRepository.deleteAll(meetingParticipantRepository.findByUser_Id(id));
        meetingParticipantRepository.flush();

        taskRepository.deleteAll(taskRepository.findByUser_IdOrderByCreatedAtAsc(id));
        taskRepository.flush();

        userRepository.deleteById(id);
        userRepository.flush();
    }
}
