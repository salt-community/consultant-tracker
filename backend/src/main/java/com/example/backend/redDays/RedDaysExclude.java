package com.example.backend.redDays;

public enum RedDaysExclude {
    WHIT_SUN_EVE("whitsunEve"),
    NEW_YEAR_EVE("newYearsEve");
    public final String name;
    private RedDaysExclude(String name) {
        this.name = name;
    }
}
