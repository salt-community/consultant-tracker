package com.example.backend;

import com.example.backend.client.timekeeper.dto.TimekeeperUserDto;
import com.example.backend.consultant.Consultant;

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
                tkUser.phone(),
                tkUser.id(),
                tkUser.responsiblePT(),
                tkUser.client(),
                "Sverige",
                true);
    }
}
