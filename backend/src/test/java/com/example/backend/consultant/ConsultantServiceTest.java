package com.example.backend.consultant;

import com.example.backend.ApplicationTestConfig;
import com.example.backend.client.timekeeper.TimekeeperClient;
import com.example.backend.client.timekeeper.dto.TimekeeperUserDto;
import com.example.backend.registeredTime.RegisteredTimeService;
import com.example.backend.tag.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = ApplicationTestConfig.class)
@ActiveProfiles("test")
class ConsultantServiceTest {
    @Autowired
    ConsultantService service;
//    @Mock
//    private ConsultantRepository repo;
    @Mock
    private TimekeeperClient tkClient;
//    @Mock
//    private RegisteredTimeService registeredTimeService;

//    @BeforeEach
//    public void setUp() {
//        this.service = new ConsultantService(repo, tkClient, registeredTimeService);
//    }

    @Test
    void resultShouldNotBeEmptyList() {
        List<Consultant> consultantList = service.getAllConsultants();
        System.out.println("consultantList = " + consultantList);
        assertFalse(consultantList.isEmpty());
    }

    @Test
    void shouldAddNewConsultant() {
        Tag mockTag = new Tag();
        mockTag.setName("mock-tag");
        List<TimekeeperUserDto> mockedTkUserList = List.of(
                new TimekeeperUserDto(
                        "MockFirstName",
                        "MockLastName",
                        "mockEmail",
                        "mockPhone",
                        List.of(mockTag),
                        1000L,
                        true,
                        "mockClient",
                        "mockResponsiblePT",
                        true
                )
        );
        Mockito.when(tkClient.getUsers()).thenReturn(mockedTkUserList);
        int totalConsultantsBefore = service.getAllConsultants().size();
        service.fetchDataFromTimekeeper();
        int totalConsultantsAfter = service.getAllConsultants().size();
        int actualResult = totalConsultantsAfter - totalConsultantsBefore;
        assertEquals(1, actualResult);
    }

}