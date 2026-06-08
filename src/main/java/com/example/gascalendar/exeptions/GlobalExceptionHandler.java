package com.example.gascalendar.exeptions;

import com.example.gascalendar.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException exception) {
        List<String> details = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        return badRequest("Request validation failed", details);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleUnreadableBody(HttpMessageNotReadableException exception) {
        return badRequest(
                "Request body is invalid",
                List.of("Check JSON syntax and enum values")
        );
    }

    @ExceptionHandler(NotFoundUserException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(NotFoundUserException exception) {
        return badRequest(
                "User does not exist",
                List.of(exception.getMessage())
        );
    }

    @ExceptionHandler(NotFoundUserTask.class)
    public ResponseEntity<ErrorResponse> handleTaskNotFound(NotFoundUserTask exception) {
        return errorResponse(
                HttpStatus.NOT_FOUND,
                "Task does not exist",
                List.of(exception.getMessage())
        );
    }

    private ResponseEntity<ErrorResponse> badRequest(String message, List<String> details) {
        return errorResponse(HttpStatus.BAD_REQUEST, message, details);
    }

    private ResponseEntity<ErrorResponse> errorResponse(HttpStatus status, String message, List<String> details) {
        ErrorResponse response = new ErrorResponse(
                false,
                message,
                status.getReasonPhrase(),
                details,
                LocalDateTime.now()
        );
        return ResponseEntity.status(status).body(response);
    }
}
