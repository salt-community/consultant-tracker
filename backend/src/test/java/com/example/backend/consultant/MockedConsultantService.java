package com.example.backend.consultant;

import java.util.ArrayList;
import java.util.List;

public class MockedConsultantService {

    private static List<Consultant> mockConsultantList = new ArrayList<>();

    public static void mockedCreateConsultant(Consultant consultant) {

        mockConsultantList.add(consultant);
    }

    public static List<Consultant> mockedGetConsultantsList() {
        return mockConsultantList;
    }
}
