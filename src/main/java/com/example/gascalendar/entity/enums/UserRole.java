package com.example.gascalendar.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Locale;

public enum UserRole {
    ADMIN, OPERATOR, VIEWER;

    @JsonCreator
    public static UserRole fromValue(String value) {
        return UserRole.valueOf(value.toUpperCase(Locale.ROOT));
    }

    @JsonValue
    public String toValue() {
        return name().toLowerCase(Locale.ROOT);
    }
}
