package com.example.backend.redDays;

import com.example.backend.client.nager.dto.RedDaysFromNagerDto;
import org.assertj.core.util.Lists;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RedDayServiceMockedData {

    public static List<RedDay> createMockedRedDayList(){
        var mockedRedDay = new RedDay(
                UUID.fromString("2e6d51fe-713a-413b-8769-0180aa60c084"),
                LocalDate.parse("2023-12-31"),
                "New Years Eve",
                "SE"
        );
        var mockedRedDay2 = new RedDay(
                UUID.fromString("2e6d51fe-713a-413b-8769-0180aa60c084"),
                LocalDate.parse("2024-01-01"),
                "New Year",
                "SE"
        );
        return Lists.newArrayList(mockedRedDay,mockedRedDay2);
    }

    public static List<RedDay> createMockedRedDaysList(){
        var mockedRedDay = new RedDay(
                UUID.fromString("2e6d51fe-713a-413b-8769-0180aa60c084"),
                LocalDate.parse("2024-01-01"),
                "New Year",
                "NO"
        );
        var mockedRedDay2 = new RedDay(
                UUID.fromString("2e6d51fe-713a-413b-8769-0180aa60c084"),
                LocalDate.parse("2024-12-24"),
                "Christmas Eve",
                "NO"
        );
        return Lists.newArrayList(mockedRedDay, mockedRedDay2);
    }

    public static List<RedDaysFromNagerDto> createMockedRedDaysFromNager(){
        RedDaysFromNagerDto mockedRedDaysFromNagerDto = new RedDaysFromNagerDto(
                LocalDate.parse("2018-12-31"),
                "New Years Eve",
                "SE");
        List<RedDaysFromNagerDto> mockedRedDaysFromNagerDtoList = new ArrayList<>();
        mockedRedDaysFromNagerDtoList.add(mockedRedDaysFromNagerDto);
        return mockedRedDaysFromNagerDtoList;
    }
    public static RedDay createMockedRedDaysFromRepository(){
        return new RedDay(UUID.fromString("45ec353f-b0f5-4a51-867e-8d0d84d11573"),
                LocalDate.parse("2018-12-31"),
                "New Years Eve",
                "SE");
    }

    public static RedDay createMockedRedDaysFromRepositoryNO(){
        return new RedDay(UUID.fromString("45ec353f-b0f5-4a51-867e-8d0d84d11573"),
                LocalDate.parse("2024-12-24"),
                "Christmas Eve",
                "NO");
    }
    public static RedDay createMockedRedDaysFromRepositorySE(){
        return new RedDay(UUID.fromString("45ec353f-b0f5-4a51-867e-8d0d84d11573"),
                LocalDate.parse("2024-12-25"),
                "Christmas",
                "SE");
    }
}
