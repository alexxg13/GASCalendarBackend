package com.example.gascalendar.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAvailabilityResponse {
    private String userId;
    private String userName;
    private String avatar;
    private Map<String, List<String>> availability;
}
