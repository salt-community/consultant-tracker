package com.example.backend.registeredTime;

import com.example.backend.consultant.dto.ConsultantTimeDto;
import org.assertj.core.util.Lists;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

public class RegisteredTimeServiceMockedData {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static List<RegisteredTime> generateMockedRegisteredTimeData(){
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
    public static List<ConsultantTimeDto> createAllWronglyRegisteredTimeMockedData(){
        ConsultantTimeDto mockedConsultantTimeDto = new ConsultantTimeDto(
                new RegisteredTimeKey(UUID.fromString("589256b4-c5db-4d77-9d50-49c0636a4c52"),
                        LocalDateTime.parse("2024-03-29 00:00", formatter)),
                LocalDateTime.parse("2024-03-29 23:59", formatter),
                "Konsult-tid",
                0D,
                "H&M");
        ConsultantTimeDto mockedConsultantTimeDto2 = new ConsultantTimeDto(
                new RegisteredTimeKey(UUID.fromString("589256b4-c5db-4d77-9d50-49c0636a4c52"),
                        LocalDateTime.parse("2024-03-30 00:00", formatter)),
                LocalDateTime.parse("2024-03-30 23:59", formatter),
                "Konsult-tid",
                0D,
                "H&M");
        return Lists.newArrayList(mockedConsultantTimeDto, mockedConsultantTimeDto2);
    }
    public static List<ConsultantTimeDto> createSomeWronglyRegisteredTimeMockedData(){
        ConsultantTimeDto mockedConsultantTimeDto = new ConsultantTimeDto(
                new RegisteredTimeKey(UUID.fromString("589256b4-c5db-4d77-9d50-49c0636a4c52"),
                        LocalDateTime.parse("2024-03-29 00:00", formatter)),
                LocalDateTime.parse("2024-03-29 23:59", formatter),
                "Konsult-tid",
                0D,
                "H&M");
        ConsultantTimeDto mockedConsultantTimeDto2 = new ConsultantTimeDto(
                new RegisteredTimeKey(UUID.fromString("589256b4-c5db-4d77-9d50-49c0636a4c52"),
                        LocalDateTime.parse("2024-03-30 00:00", formatter)),
                LocalDateTime.parse("2024-03-30 23:59", formatter),
                "Konsult-tid",
                0D,
                "H&M");
        ConsultantTimeDto mockedConsultantTimeDto3 = new ConsultantTimeDto(
                new RegisteredTimeKey(UUID.fromString("589256b4-c5db-4d77-9d50-49c0636a4c52"),
                        LocalDateTime.parse("2024-03-28 00:00", formatter)),
                LocalDateTime.parse("2024-03-28 23:59", formatter),
                "Konsult-tid",
                0D,
                "H&M");
        return Lists.newArrayList(mockedConsultantTimeDto, mockedConsultantTimeDto2, mockedConsultantTimeDto3);
    }

    public static List<ConsultantTimeDto> createSemesterRegisteredWithOHours(){
        ConsultantTimeDto mockedConsultantTimeDto = new ConsultantTimeDto(
                new RegisteredTimeKey(UUID.fromString("589256b4-c5db-4d77-9d50-49c0636a4c52"),
                        LocalDateTime.parse("2024-03-29 00:00", formatter)),
                LocalDateTime.parse("2024-03-29 23:59", formatter),
                "Semester",
                0D,
                "H&M");
        ConsultantTimeDto mockedConsultantTimeDto2 = new ConsultantTimeDto(
                new RegisteredTimeKey(UUID.fromString("589256b4-c5db-4d77-9d50-49c0636a4c52"),
                        LocalDateTime.parse("2024-03-30 00:00", formatter)),
                LocalDateTime.parse("2024-03-30 23:59", formatter),
                "Semester",
                0D,
                "H&M");
        ConsultantTimeDto mockedConsultantTimeDto3 = new ConsultantTimeDto(
                new RegisteredTimeKey(UUID.fromString("589256b4-c5db-4d77-9d50-49c0636a4c52"),
                        LocalDateTime.parse("2024-03-28 00:00", formatter)),
                LocalDateTime.parse("2024-03-28 23:59", formatter),
                "Semester",
                0D,
                "H&M");
        return Lists.newArrayList(mockedConsultantTimeDto, mockedConsultantTimeDto2, mockedConsultantTimeDto3);
    }

}
