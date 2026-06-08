package com.example.gascalendar.controller;

import com.example.gascalendar.dto.response.ApiResponse;
import com.example.gascalendar.dto.response.CalendarItemsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Validated
@RequestMapping("/api/calendar")
@Tag(name = "Calendar", description = "Calendar-based task and meeting views")
public interface CalendarController {
    @Operation(summary = "Get calendar items for date range")
    @GetMapping("/items")
    ResponseEntity<ApiResponse<CalendarItemsResponse>> getCalendarItems(
            @NotNull @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @NotNull @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            HttpSession session
    );
}
