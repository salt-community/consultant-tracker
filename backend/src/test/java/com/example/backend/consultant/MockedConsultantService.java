package com.example.backend.consultant;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MockedConsultantService {

    private static Consultant firstMockConsultant = new Consultant(
            UUID.fromString("aac670d6-3038-4fca-95be-2669aaf0b549"),
            "Hozier Fodor",
                    "hozier.fodor@gmail.com",
                    null,
                    1122L,
                    "James Stephen",
                    "H&M",
                    "Sverige",
                    true);
    private static List<Consultant> mockConsultantList = new ArrayList<>();


    public static Consultant mockedCreateConsultant(Consultant consultant) {
        MockedConsultantService.mockConsultantList.add(firstMockConsultant);
        boolean isAdded = MockedConsultantService.mockConsultantList.add(consultant);
        System.out.println("isAdded = " + isAdded);
        return consultant;
    }

    public static List<Consultant> mockedGetConsultantsList() {
        return mockConsultantList;
    }
}
