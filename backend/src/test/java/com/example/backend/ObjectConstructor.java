package com.example.backend;

import com.example.backend.client.timekeeper.dto.TimekeeperUserDto;
import com.example.backend.consultant.Consultant;
import com.example.backend.consultant.dto.ConsultantTimeDto;
import com.example.backend.registeredTime.RegisteredTimeKey;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ObjectConstructor {

    public static List<TimekeeperUserDto> getListOfTimekeeperUserDto(int listSize) {
        List<TimekeeperUserDto> resultList = new ArrayList<>();
        for (int i = 0; i < listSize; i++) {
            TimekeeperUserDto mockedUserToAdd = new TimekeeperUserDto(
                    "mock First Name ".concat(String.valueOf(i)),
                    "mock Last Name ".concat(String.valueOf(i)),
                    "mock email ".concat(String.valueOf(i)),
                    null,
                    new ArrayList<>(),
                    300L + i,
                    true,
                    "mock client",
                    "mock responsiblePT",
                    true
            );
            resultList.add(mockedUserToAdd);
        }
        return resultList;
    }

    public static Consultant convertTimekeeperUserDtoToConsultant(TimekeeperUserDto tkUser) {
        return new Consultant(
                UUID.randomUUID(),
                tkUser.firstName().trim().concat(" ").concat(tkUser.lastName()).trim(),
                tkUser.email(),
                tkUser.id(),
                null,
                true,
                tkUser.client(),
                "Sverige",
                null);

    }

    public static TimekeeperUserDto convertConsultantToTimekeeperUserDto(Consultant consultant) {
        String[] firstAndLastName = consultant.getFullName().split(" ");
        return new TimekeeperUserDto(
                firstAndLastName[0],
                firstAndLastName[1],
                consultant.getEmail(),
                null,
                null,
                consultant.getTimekeeperId(),
                consultant.isActive(),
                consultant.getClient(),
                consultant.getSaltUser().getFullName() !=  null ? consultant.getSaltUser().getFullName() : "",
                true);
    }

    public static List<ConsultantTimeDto> getTimekeeperRegisteredTime(UUID consultantId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        ConsultantTimeDto mockConsultantTime1 = new ConsultantTimeDto(
                new RegisteredTimeKey(consultantId, LocalDateTime.parse("2024-03-28 00:00", formatter)),
                LocalDateTime.parse("2024-03-28 23:59", formatter),
                "Konsult-tid",
                8D,
                "H&M");
        ConsultantTimeDto mockConsultantTime2 = new ConsultantTimeDto(
                new RegisteredTimeKey(consultantId, LocalDateTime.parse("2024-03-29 00:00", formatter)),
                LocalDateTime.parse("2024-03-29 23:59", formatter),
                "Konsult-tid",
                8D,
                "H&M");
        ConsultantTimeDto mockConsultantTime3 = new ConsultantTimeDto(
                new RegisteredTimeKey(consultantId, LocalDateTime.parse("2024-03-30 00:00", formatter)),
                LocalDateTime.parse("2024-03-30 23:59", formatter),
                "Konsult-tid",
                8D,
                "H&M");

        return List.of(mockConsultantTime1, mockConsultantTime2, mockConsultantTime3);
    }
}
