package com.example.backend.consultant;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MockedConsultantService {

    private static List<Consultant> mockConsultantList = new ArrayList<>();


    public static Consultant mockedCreateConsultant(Consultant consultant) {
        MockedConsultantService.mockConsultantList.add(consultant);
        return consultant;
    }

    public static List<Consultant> mockedGetConsultantsList() {
        return mockConsultantList;
    }

    public static void clearList() {
        MockedConsultantService. mockConsultantList.clear();
    }

    public static Consultant mockedUpdateConsultant(Consultant consultant) {
        for (Consultant c: MockedConsultantService.mockConsultantList) {
            if (c.getId() == consultant.getId()) {
                c.setActive(consultant.isActive());
            }
        }
        return consultant;
    }
}
