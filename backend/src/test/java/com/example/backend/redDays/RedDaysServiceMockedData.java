package com.example.backend.redDays;

import org.assertj.core.util.Lists;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class RedDaysServiceMockedData {
    public static List<RedDays> createMockedRedDaysDataNO(){
        RedDays mockedRedDay = new RedDays(
                UUID.fromString("2e6d51fe-713a-413b-8769-0180aa60c084"),
                LocalDate.parse("2024-01-01"),
                "Red day test",
                "NO"
        );
        RedDays mockedRedDay2 = new RedDays(
                UUID.fromString("2e6d51fe-713a-413b-8769-0180aa60c084"),
                LocalDate.parse("2024-12-24"),
                "Red day test2",
                "NO"
        );
        return Lists.newArrayList(mockedRedDay, mockedRedDay2);
    }

    public static List<RedDays> createMockedRedDayList(){
        var mockedRedDay = new RedDays(
                UUID.fromString("2e6d51fe-713a-413b-8769-0180aa60c084"),
                LocalDate.parse("2024-01-01"),
                "Red day test",
                "SE"
        );
        return Lists.newArrayList(mockedRedDay);
    }

    public static List<RedDays> createMockedRedDaysList(){
        var mockedRedDay = new RedDays(
                UUID.fromString("2e6d51fe-713a-413b-8769-0180aa60c084"),
                LocalDate.parse("2024-01-01"),
                "Red day test",
                "NO"
        );
        var mockedRedDay2 = new RedDays(
                UUID.fromString("2e6d51fe-713a-413b-8769-0180aa60c084"),
                LocalDate.parse("2024-12-24"),
                "Red day test2",
                "NO"
        );
        return Lists.newArrayList(mockedRedDay, mockedRedDay2);
    }

}
