package com.example.backend.registeredTime;

import com.example.backend.consultant.Consultant;
import com.example.backend.consultant.dto.ConsultantTimeDto;
import com.example.backend.registeredTime.dto.RegisteredTimeDto;
import com.example.backend.registeredTime.dto.RegisteredTimeResponseDto;
import org.assertj.core.util.Lists;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RegisteredTimeServiceMockedData {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter formatterSeconds = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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

    public static List<RegisteredTime> createMockedRegisteredTimeList() {
        RegisteredTime mockedRegisteredTime = new RegisteredTime(new RegisteredTimeKey(
                UUID.fromString("68c670d6-3038-4fca-95be-2669aaf0b549"),
                LocalDateTime.parse("2024-05-20 00:00:00", formatterSeconds)),
                "Konsult-tid",
                LocalDateTime.parse("2024-05-20 23:59:59", formatterSeconds),
                8D,
                "AstraZeneca");
        RegisteredTime mockedRegisteredTime2 = new RegisteredTime(new RegisteredTimeKey(
                UUID.fromString("68c670d6-3038-4fca-95be-2669aaf0b549"),
                LocalDateTime.parse("2024-05-21 00:00:00", formatterSeconds)),
                "Konsult-tid",
                LocalDateTime.parse("2024-05-21 23:59:59", formatterSeconds),
                8D,
                "AstraZeneca");
        RegisteredTime mockedRegisteredTime3 = new RegisteredTime(new RegisteredTimeKey(
                UUID.fromString("68c670d6-3038-4fca-95be-2669aaf0b549"),
                LocalDateTime.parse("2024-05-22 00:00:00", formatterSeconds)),
                "Konsult-tid",
                LocalDateTime.parse("2024-05-22 23:59:59", formatterSeconds),
                8D,
                "AstraZeneca");
        RegisteredTime mockedRegisteredTime4 = new RegisteredTime(new RegisteredTimeKey(
                UUID.fromString("68c670d6-3038-4fca-95be-2669aaf0b549"),
                LocalDateTime.parse("2024-05-23 00:00:00", formatterSeconds)),
                "Konsult-tid",
                LocalDateTime.parse("2024-05-23 23:59:59", formatterSeconds),
                8D,
                "AstraZeneca");
        RegisteredTime mockedRegisteredTime5 = new RegisteredTime(new RegisteredTimeKey(
                UUID.fromString("68c670d6-3038-4fca-95be-2669aaf0b549"),
                LocalDateTime.parse("2024-05-24 00:00:00", formatterSeconds)),
                "Konsult-tid",
                LocalDateTime.parse("2024-05-24 23:59:59", formatterSeconds),
                8D,
                "AstraZeneca");
        RegisteredTime mockedRegisteredTime6 = new RegisteredTime(new RegisteredTimeKey(
                UUID.fromString("68c670d6-3038-4fca-95be-2669aaf0b549"),
                LocalDateTime.parse("2024-05-27 00:00:00", formatterSeconds)),
                "Konsult-tid",
                LocalDateTime.parse("2024-05-27 23:59:59", formatterSeconds),
                8D,
                "AstraZeneca");
        RegisteredTime mockedRegisteredTime7 = new RegisteredTime(new RegisteredTimeKey(
                UUID.fromString("68c670d6-3038-4fca-95be-2669aaf0b549"),
                LocalDateTime.parse("2024-06-28 00:00:00", formatterSeconds)),
                "Sjuk",
                LocalDateTime.parse("2024-06-28 23:59:59", formatterSeconds),
                8D,
                "PGP");
        RegisteredTime mockedRegisteredTime8 = new RegisteredTime(new RegisteredTimeKey(
                UUID.fromString("68c670d6-3038-4fca-95be-2669aaf0b549"),
                LocalDateTime.parse("2024-07-01 00:00:00", formatterSeconds)),
                "Semester",
                LocalDateTime.parse("2024-07-01 23:59:59", formatterSeconds),
                8D,
                "PGP");
        RegisteredTime mockedRegisteredTime9 = new RegisteredTime(new RegisteredTimeKey(
                UUID.fromString("68c670d6-3038-4fca-95be-2669aaf0b549"),
                LocalDateTime.parse("2024-07-02 00:00:00", formatterSeconds)),
                "Semester",
                LocalDateTime.parse("2024-07-02 23:59:59", formatterSeconds),
                8D,
                "PGP");
        RegisteredTime mockedRegisteredTime10 = new RegisteredTime(new RegisteredTimeKey(
                UUID.fromString("68c670d6-3038-4fca-95be-2669aaf0b549"),
                LocalDateTime.parse("2024-08-01 00:00:00", formatterSeconds)),
                "Konsult-tid",
                LocalDateTime.parse("2024-08-01 23:59:59", formatterSeconds),
                8D,
                "Swedbank");
        RegisteredTime mockedRegisteredTime11 = new RegisteredTime(new RegisteredTimeKey(
                UUID.fromString("68c670d6-3038-4fca-95be-2669aaf0b549"),
                LocalDateTime.parse("2024-08-02 00:00:00", formatterSeconds)),
                "Konsult-tid",
                LocalDateTime.parse("2024-08-02 23:59:59", formatterSeconds),
                8D,
                "Swedbank");
        RegisteredTime mockedRegisteredTime12 = new RegisteredTime(new RegisteredTimeKey(
                UUID.fromString("68c670d6-3038-4fca-95be-2669aaf0b549"),
                LocalDateTime.parse("2024-08-02 00:00:00", formatterSeconds)),
                "Konsult-tid",
                LocalDateTime.parse("2024-08-02 23:59:59", formatterSeconds),
                8D,
                "Swedbank");
        RegisteredTime mockedRegisteredTime13 = new RegisteredTime(new RegisteredTimeKey(
                UUID.fromString("68c670d6-3038-4fca-95be-2669aaf0b549"),
                LocalDateTime.parse("2024-08-05 00:00:00", formatterSeconds)),
                "Konsult-tid",
                LocalDateTime.parse("2024-08-05 23:59:59", formatterSeconds),
                8D,
                "Swedbank");
        RegisteredTime mockedRegisteredTime14 = new RegisteredTime(new RegisteredTimeKey(
                UUID.fromString("68c670d6-3038-4fca-95be-2669aaf0b549"),
                LocalDateTime.parse("2024-08-06 00:00:00", formatterSeconds)),
                "Konsult-tid",
                LocalDateTime.parse("2024-08-06 23:59:59", formatterSeconds),
                8D,
                "Swedbank");
        RegisteredTime mockedRegisteredTime15 = new RegisteredTime(new RegisteredTimeKey(
                UUID.fromString("68c670d6-3038-4fca-95be-2669aaf0b549"),
                LocalDateTime.parse("2024-08-07 00:00:00", formatterSeconds)),
                "Konsult-tid",
                LocalDateTime.parse("2024-08-07 23:59:59", formatterSeconds),
                8D,
                "Swedbank");
        RegisteredTime mockedRegisteredTime16 = new RegisteredTime(new RegisteredTimeKey(
                UUID.fromString("68c670d6-3038-4fca-95be-2669aaf0b549"),
                LocalDateTime.parse("2024-08-08 00:00:00", formatterSeconds)),
                "Konsult-tid",
                LocalDateTime.parse("2024-08-08 23:59:59", formatterSeconds),
                8D,
                "Swedbank");
        RegisteredTime mockedRegisteredTime17 = new RegisteredTime(new RegisteredTimeKey(
                UUID.fromString("68c670d6-3038-4fca-95be-2669aaf0b549"),
                LocalDateTime.parse("2024-08-09 00:00:00", formatterSeconds)),
                "Konsult-tid",
                LocalDateTime.parse("2024-08-09 23:59:59", formatterSeconds),
                8D,
                "Swedbank");
        return Lists.newArrayList(mockedRegisteredTime,
                mockedRegisteredTime2,
                mockedRegisteredTime3,
                mockedRegisteredTime4,
                mockedRegisteredTime5,
                mockedRegisteredTime6,
                mockedRegisteredTime7,
                mockedRegisteredTime8,
                mockedRegisteredTime9,
                mockedRegisteredTime10,
                mockedRegisteredTime11,
                mockedRegisteredTime12,
                mockedRegisteredTime13,
                mockedRegisteredTime14,
                mockedRegisteredTime15,
                mockedRegisteredTime16,
                mockedRegisteredTime17);
    }

    public static List<RegisteredTimeResponseDto> createGroupedRegisteredTimeDtoResponse(){
        RegisteredTimeResponseDto mockedRegisteredTimeResult = new RegisteredTimeResponseDto(
                UUID.randomUUID(),
                LocalDateTime.parse("2024-05-20 00:00:00",formatterSeconds),
                LocalDateTime.parse("2024-05-27 23:59:59",formatterSeconds),
                "Konsult-tid",
                "AstraZeneca",
                6);
        RegisteredTimeResponseDto mockedRegisteredTimeResult2 = new RegisteredTimeResponseDto(
                UUID.randomUUID(),
                LocalDateTime.parse("2024-06-28 00:00:00",formatterSeconds),
                LocalDateTime.parse("2024-06-30 23:59:59",formatterSeconds),
                "Sjuk",
                "PGP",
                1);
        RegisteredTimeResponseDto mockedRegisteredTimeResult3 = new RegisteredTimeResponseDto(
                UUID.randomUUID(),
                LocalDateTime.parse("2024-07-01 00:00:00",formatterSeconds),
                LocalDateTime.parse("2024-07-02 23:59:59",formatterSeconds),
                "Semester",
                "PGP",
                2);
        RegisteredTimeResponseDto mockedRegisteredTimeResult4 = new RegisteredTimeResponseDto(
                UUID.randomUUID(),
                LocalDateTime.parse("2024-07-01 00:00:00",formatterSeconds),
                LocalDateTime.parse("2024-07-02 23:59:59",formatterSeconds),
                "Semester",
                "PGP",
                2);
        RegisteredTimeResponseDto mockedRegisteredTimeResult5 = new RegisteredTimeResponseDto(
                UUID.randomUUID(),
                LocalDateTime.parse("2024-05-28 00:00:00",formatterSeconds),
                LocalDateTime.parse("2024-06-27 23:59:59",formatterSeconds),
                "No Registered Time",
                "No Registered Time",
                21);
        RegisteredTimeResponseDto mockedRegisteredTimeResult6 = new RegisteredTimeResponseDto(
                UUID.randomUUID(),
                LocalDateTime.parse("2024-07-03 00:00:00",formatterSeconds),
                LocalDateTime.parse("2024-07-31 23:59:59",formatterSeconds),
                "No Registered Time",
                "No Registered Time",
                21);
        RegisteredTimeResponseDto mockedRegisteredTimeResult7 = new RegisteredTimeResponseDto(
                UUID.randomUUID(),
                LocalDateTime.parse("2024-08-01 00:00:00",formatterSeconds),
                LocalDateTime.parse("2024-08-09 23:59:59",formatterSeconds),
                "No Registered Time",
                "No Registered Time",
                7);
        return Lists.newArrayList(mockedRegisteredTimeResult,
                mockedRegisteredTimeResult2,
                mockedRegisteredTimeResult3,
                mockedRegisteredTimeResult4,
                mockedRegisteredTimeResult5,
                mockedRegisteredTimeResult6,
                mockedRegisteredTimeResult7
                );
    }

}
