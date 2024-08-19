package com.example.backend.redDay;

import com.example.backend.client.nager.dto.RedDaysFromNagerDto;
import org.assertj.core.util.Lists;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RedDayServiceMockedData {

    public static List<RedDay> createMockedRedDayList(){
        var mockedRedDay = new RedDay(new RedDayKey(LocalDate.parse("2023-12-31"), "SE"),
                "New Years Eve"
        );
        var mockedRedDay2 = new RedDay( new RedDayKey(LocalDate.parse("2024-01-01"), "SE"),
                "New Year"
        );
        return Lists.newArrayList(mockedRedDay,mockedRedDay2);
    }

    public static List<RedDay> createMockedRedDaysList(){
        var mockedRedDay = new RedDay( new RedDayKey(LocalDate.parse("2024-01-01"), "NO"),
                "New Year"
        );
        var mockedRedDay2 = new RedDay( new RedDayKey(LocalDate.parse("2024-12-24"), "NO"),
                "Christmas Eve"
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
        return new RedDay(new RedDayKey(LocalDate.parse("2018-12-31"),"SE"),
                "New Years Eve");
    }

    public static RedDay createMockedRedDaysFromRepositoryNO(){
        return new RedDay(new RedDayKey(LocalDate.parse("2024-12-24"),"NO"),
                "Christmas Eve");

    }
    public static RedDay createMockedRedDaysFromRepositorySE(){
        return new RedDay(new RedDayKey(LocalDate.parse("2024-12-25"),"SE"),
                "Christmas");
    }
}
