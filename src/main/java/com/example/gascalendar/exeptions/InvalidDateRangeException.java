package com.example.gascalendar.exeptions;

public class InvalidDateRangeException extends RuntimeException {
    public InvalidDateRangeException() {
        super("startDate must be before or equal to endDate");
    }
}
