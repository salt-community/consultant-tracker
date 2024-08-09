package com.example.backend.registeredTime;

import org.assertj.core.util.Lists;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

public class RegisteredTimeMockedData {
    public static List<RegisteredTime> generateMockedRegisteredTimeData(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        RegisteredTime mockedTimeItem = new RegisteredTime(
                new RegisteredTimeKey(
                        UUID.randomUUID(),
                        LocalDateTime.parse("2024-01-02 00:00", formatter)),
                "Konsult-tid",
                LocalDateTime.parse("2024-01-05 23:59", formatter),
                8D,
                "H&M");
        RegisteredTime mockedTimeItem2 = new RegisteredTime(
                new RegisteredTimeKey(
                        UUID.randomUUID(),
                        LocalDateTime.parse("2024-12-24 00:00", formatter)),
                "Semester",
                LocalDateTime.parse("2024-12-26 23:59", formatter),
                8D,
                "H&M");
        return Lists.newArrayList(mockedTimeItem, mockedTimeItem2);
    }

}
