package com.example.backend.meetings_schedule.dto;

import com.example.backend.meetings_schedule.MeetingsSchedule;

import java.time.temporal.WeekFields;
import java.util.Locale;

public record MeetingsDto(int year, int weekNumber, String title) {

    public static MeetingsDto toDto(MeetingsSchedule meetingsSchedule) {
        return new MeetingsDto(
                meetingsSchedule.getDate().getYear(),
                meetingsSchedule.getDate().get(WeekFields.of(Locale.ENGLISH).weekOfYear()),
                meetingsSchedule.getId().getTitle().name());
    }
}
