package com.example.gascalendar.repository;

import com.example.gascalendar.entity.User;
import com.example.gascalendar.entity.enums.UserRole;
import com.example.gascalendar.entity.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
    boolean existsByEmailAndPasswordHash(String email, String passwordHash);

    List<User> findByRole(UserRole role);

    List<User> findByStatus(UserStatus status);

    List<User> findByRoleAndStatus(UserRole role, UserStatus status);
}
