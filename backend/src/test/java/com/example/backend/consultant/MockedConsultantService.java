package com.example.backend.consultant;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MockedConsultantService {

    private static List<Consultant> mockConsultantList = new ArrayList<>();


    public static Consultant mockedCreateConsultant(Consultant consultant) {
        MockedConsultantService.mockConsultantList.add(consultant);
        return consultant;
    }

    public static List<Consultant> mockedGetConsultantsList() {
        return mockConsultantList;
    }
}
