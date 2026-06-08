package com.example.gascalendar.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonAvailabilityResponse {
    private List<String> userIds;
    private Map<String, List<String>> availability;
}
