package com.example.gascalendar.exeptions;

public class NotFoundUserException extends RuntimeException {
    public NotFoundUserException(String userId) {
        super("User with id '" + userId + "' does not exist");
    }
}
