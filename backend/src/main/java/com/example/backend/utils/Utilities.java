package com.example.backend.utils;

public class Utilities {
    public static boolean isWeekend(int day) {
        int SATURDAY = 6;
        int SUNDAY = 7;
        return day == SATURDAY || day == SUNDAY;
    }
}
