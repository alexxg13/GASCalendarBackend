package com.example.gascalendar.service.impl;

import com.example.gascalendar.dto.request.TaskCreateRequest;
import com.example.gascalendar.dto.request.TaskMoveRequest;
import com.example.gascalendar.dto.request.TaskUpdateRequest;
import com.example.gascalendar.dto.response.ColumnResponse;
import com.example.gascalendar.dto.response.TaskResponse;
import com.example.gascalendar.entity.Task;
import com.example.gascalendar.entity.User;
import com.example.gascalendar.entity.enums.TaskColumn;
import com.example.gascalendar.exeptions.NotFoundUserException;
import com.example.gascalendar.exeptions.NotFoundUserTask;
import com.example.gascalendar.mappers.TasksMapper;
import com.example.gascalendar.repository.TaskRepository;
import com.example.gascalendar.repository.UserRepository;
import com.example.gascalendar.service.TasksService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
@Service
@Data
public class TasksServiceImpl implements TasksService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TasksMapper tasksMapper;

    @Override
    public List<ColumnResponse> getColumns(String userId) {
        List<Task> tasks = taskRepository.findByUser_IdOrderByCreatedAtAsc(userId);

        return Arrays.stream(TaskColumn.values())
                .map(taskColumn -> {
                     return new ColumnResponse(
                        taskColumn,
                             taskColumn.toString(),
                            tasks.stream().filter(task -> task.getColumn() == taskColumn).map(task -> tasksMapper.toResponse(task)).toList(), taskColumn.ordinal()
                     );
                }).toList();
    }

    @Override
    public TaskResponse createTask(String userId, TaskCreateRequest request) {
        Task task = tasksMapper.toCreateEntity(request);

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new NotFoundUserException(userId);
        }

        task.setUser(user);
        task.setCompleted(false);
        task = taskRepository.save(task);
        return tasksMapper.toResponse(task);
    }

    @Override
    @Transactional
    public TaskResponse updateTask(String userId, String taskId, TaskUpdateRequest request) {
        Task task = getUserTask(userId, taskId);
        tasksMapper.updateEntity(request, task);
        return tasksMapper.toResponse(task);
    }

    @Override
    public void deleteTask(String userId, String taskId) {
        taskRepository.delete(getUserTask(userId, taskId));
    }

    @Override
    @Transactional
    public void moveTask(String userId, String taskId, TaskMoveRequest request) {
        Task task = getUserTask(userId, taskId);
        task.setColumn(request.getToColumnId());
        task.setCompleted(request.getToColumnId() == TaskColumn.COMPLETED);
    }

    @Override
    @Transactional
    public TaskResponse toggleTaskComplete(String userId, String taskId) {
        Task task = getUserTask(userId, taskId);
        boolean completed = !Boolean.TRUE.equals(task.getCompleted());
        task.setCompleted(completed);
        task.setColumn(completed ? TaskColumn.COMPLETED : TaskColumn.ACTIONS);

        return tasksMapper.toResponse(task);
    }

    @Override
    public List<TaskResponse> getTasks(String userId) {
        List<Task> tasks = taskRepository.findByUser_IdOrderByCreatedAtAsc(userId);
        return tasks.stream().map(task -> tasksMapper.toResponse(task)).toList();

    }

    private Task getUserTask(String userId, String taskId) {
        return taskRepository.findByIdAndUser_Id(taskId, userId)
                .orElseThrow(() -> new NotFoundUserTask(userId, taskId));
    }
}
