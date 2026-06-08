package com.example.gascalendar.exeptions;

public class MeetingNotFoundException extends RuntimeException {
    public MeetingNotFoundException(String meetingId) {
        super("Meeting with id '" + meetingId + "' does not exist or is not accessible");
    }
}
