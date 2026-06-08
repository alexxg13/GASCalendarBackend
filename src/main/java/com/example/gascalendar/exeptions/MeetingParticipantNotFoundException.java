package com.example.gascalendar.exeptions;

public class MeetingParticipantNotFoundException extends RuntimeException {
    public MeetingParticipantNotFoundException(String userId, String meetingId) {
        super("User '" + userId + "' is not a participant of meeting '" + meetingId + "'");
    }
}
