package com.example.backend.consultant;

import com.example.backend.ApplicationTestConfig;
import com.example.backend.client.timekeeper.TimekeeperClient;
import com.example.backend.client.timekeeper.dto.TimekeeperUserDto;
import com.example.backend.registeredTime.RegisteredTimeService;
import com.example.backend.tag.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = ApplicationTestConfig.class)
//@ActiveProfiles("test")
class ConsultantServiceTest extends ApplicationTestConfig {
    @Mock
    TimekeeperClient tkClient;
    @Mock
    ConsultantRepository repo;
    @Mock
    RegisteredTimeService registeredTimeService;
    @InjectMocks
    ConsultantService consultantService;

    @Test
    void resultShouldNotBeEmptyList() {
        List<Consultant> mockedList = List.of(new Consultant(), new Consultant());
        Mockito.when(repo.findAll()).thenReturn(mockedList);
        List<Consultant> consultantList = consultantService.getAllConsultants();
        System.out.println("consultantList = " + consultantList);
        assertFalse(consultantList.isEmpty());
    }
// the way the methods are set up this test doesn't make sense in unit testing
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
        int totalConsultantsBefore = consultantService.getAllConsultants().size();
        consultantService.fetchDataFromTimekeeper();
        int totalConsultantsAfter = consultantService.getAllConsultants().size();
        System.out.println("2nd test: totalConsultantsAfter = " + totalConsultantsAfter);
        int actualResult = totalConsultantsAfter - totalConsultantsBefore;
        assertEquals(1, actualResult);
    }

    @Test
    void getCountryCodeByConsultantId() {
        Mockito.when(repo.findCountryById(UUID.fromString("223152ac-6af6-4a4b-b86b-53c0707f433c"))).thenReturn("Sverige");
        String result = consultantService.getCountryCodeByConsultantId(UUID.fromString("223152ac-6af6-4a4b-b86b-53c0707f433c"));
        System.out.println("result = " + result);
        assertEquals("Sverige", result);
    }

//    @Test
//    void shouldReturnConsultantResponseListDto() {
////        testing getAllConsultantDtos()
//    }

}