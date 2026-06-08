package com.example.gascalendar.repository;

import com.example.gascalendar.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, String> {
    List<Task> findByUser_IdOrderByCreatedAtAsc(String userId);

    Optional<Task> findByIdAndUser_Id(String taskId, String userId);

    List<Task> findByUser_IdAndTaskDateBetweenOrderByTaskDateAscTaskTimeAsc(
            String userId,
            LocalDate startDate,
            LocalDate endDate
    );
}
