package com.example.gascalendar.exeptions;

public class NotFoundUserTask extends RuntimeException {
    public NotFoundUserTask(String userId, String taskId) {
        super("Task with id '" + taskId + "' does not exist for user '" + userId + "'");
    }
}
