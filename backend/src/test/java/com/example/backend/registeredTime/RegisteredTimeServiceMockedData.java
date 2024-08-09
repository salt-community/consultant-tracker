package com.example.backend.registeredTime;

import com.example.backend.consultant.Consultant;
import com.example.backend.consultant.dto.ConsultantTimeDto;
import org.assertj.core.util.Lists;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RegisteredTimeServiceMockedData {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static List<RegisteredTime> generateMockedRegisteredTimeData() {
        RegisteredTime mockedTimeItem = new RegisteredTime(
                new RegisteredTimeKey(
                        UUID.fromString("589256b4-c5db-4d77-9d50-49c0636a4c52"),
                        LocalDateTime.parse("2024-01-02 00:00", formatter)),
                "Konsult-tid",
                LocalDateTime.parse("2024-01-05 23:59", formatter),
                8D,
                "H&M");
        RegisteredTime mockedTimeItem2 = new RegisteredTime(
                new RegisteredTimeKey(
                        UUID.fromString("68c670d6-3038-4fca-95be-2669aaf0b549"),
                        LocalDateTime.parse("2024-12-24 00:00", formatter)),
                "Semester",
                LocalDateTime.parse("2024-12-26 23:59", formatter),
                8D,
                "H&M");
        return Lists.newArrayList(mockedTimeItem, mockedTimeItem2);
    }

    public static RegisteredTime createMockedLasWorkTimeRegistered() {
        return new RegisteredTime(
                new RegisteredTimeKey(
                        UUID.fromString("589256b4-c5db-4d77-9d50-49c0636a4c52"),
                        LocalDateTime.parse("2024-01-02 00:00", formatter)),
                "Konsult-tid",
                LocalDateTime.parse("2024-01-05 23:59", formatter),
                8D,
                "H&M");
    }

    public static List<ConsultantTimeDto> createAllWronglyRegisteredTimeMockedData() {
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

    public static List<ConsultantTimeDto> createSomeWronglyRegisteredTimeMockedData() {
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

    public static List<ConsultantTimeDto> createSemesterRegisteredWithOHours() {
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

    public static List<Consultant> createMockedListOfConsultants() {
        Consultant mockedConsultant = new Consultant(
                UUID.fromString("68c670d6-3038-4fca-95be-2669aaf0b549"),
                "John Doe",
                "john.doe@gmail.com",
                null,
                1111L,
                "Jane Doe",
                "H&M",
                "Sverige",
                true);
        Consultant mockedConsultant2 = new Consultant(
                UUID.fromString("0239ceac-5e65-40a6-a949-5492c22b22e3"),
                "John Doe2",
                "john.doe2@gmail.com",
                null,
                2222L,
                "Jane Doe2",
                "H&M2",
                "Sverige",
                true);
        return Lists.newArrayList(mockedConsultant, mockedConsultant2);
    }

    public static List<ConsultantTimeDto> generateMockedExpectedResult() {
        List<RegisteredTime> mockedListOfRegisteredTimes = Lists.newArrayList();
        mockedListOfRegisteredTimes.addAll(generateMockedRegisteredTimeData());
        mockedListOfRegisteredTimes.addAll(generateMockedRegisteredTimeData());
        return mockedListOfRegisteredTimes.stream().map(el -> new ConsultantTimeDto(
                el.getId(),
                el.getEndDate(),
                el.getType(),
                el.getTotalHours(),
                el.getProjectName())).toList();

    }

}
