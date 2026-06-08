package com.example.gascalendar.entity.enums;

public enum TaskColumn {
    GOALS, ACTIONS, STEPS, COMPLETED;

    public TaskColumn next() {
        return switch (this) {
            case GOALS -> ACTIONS;
            case ACTIONS -> STEPS;
            case STEPS, COMPLETED -> COMPLETED;
        };
    }
}
