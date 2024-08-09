package com.example.backend.registeredTime;

import com.example.backend.ApplicationTestConfig;
import com.example.backend.client.timekeeper.TimekeeperClient;
import com.example.backend.consultant.ConsultantService;
import com.example.backend.consultant.dto.ConsultantTimeDto;
import com.example.backend.redDays.RedDaysService;
import com.example.backend.utils.Utilities;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;
@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = ApplicationTestConfig.class)
public class StaticRegisteredTimeServiceTest {
    @Mock
    private RegisteredTimeRepository registeredTimeRepository;
    @Mock
    private ConsultantService consultantService;
    @Mock
    private TimekeeperClient timekeeperClient;
    @Mock
    private RedDaysService redDaysService;
    private RegisteredTimeService registeredTimeService;
    private MockedStatic<Utilities> mockUtilities;

    @BeforeEach
    public void setUp() {
        this.registeredTimeService = new RegisteredTimeService(
                registeredTimeRepository,
                consultantService,
                timekeeperClient,
                redDaysService);
        mockUtilities = mockStatic(Utilities.class);
    }

    @AfterEach
    public void tearDown() {
        mockUtilities.close();
    }

    @Test
    public void shouldReturnNoItemsWhenListIncludesOnlyWronglyRegisteredTime(){
        Mockito.lenient().when(redDaysService.isRedDay(
                LocalDate.parse("2024-03-29"),
                UUID.fromString("589256b4-c5db-4d77-9d50-49c0636a4c52"))).thenReturn(true);
        mockUtilities.when(()-> Utilities.isWeekend(6)).thenReturn(true);
        List<ConsultantTimeDto> mockedTimeItemsList = RegisteredTimeServiceMockedData.createAllWronglyRegisteredTimeMockedData();
        List<ConsultantTimeDto> actualResult = registeredTimeService.filterOutIncorrectlyRegisteredTime(mockedTimeItemsList);
        assertEquals(0, actualResult.size());
    }
    @Test
    public void shouldReturnOneTimeItemWhenListIncludesOneCorrectlyRegisteredItem() {
        Mockito.lenient().when(redDaysService.isRedDay(
                LocalDate.parse("2024-03-29"),
                UUID.fromString("589256b4-c5db-4d77-9d50-49c0636a4c52"))).thenReturn(true);
        mockUtilities.when(() -> Utilities.isWeekend(6)).thenReturn(true);
        List<ConsultantTimeDto> mockedTimeItemsList = RegisteredTimeServiceMockedData.createSomeWronglyRegisteredTimeMockedData();
        List<ConsultantTimeDto> actualResult = registeredTimeService.filterOutIncorrectlyRegisteredTime(mockedTimeItemsList);
        assertEquals(1, actualResult.size());
    }

    @Test
    public void shouldReturnThreeTimeItemsWhenListIncludesIncorrectlyRegisteredItemButTypeSemester() {
        Mockito.lenient().when(redDaysService.isRedDay(
                LocalDate.parse("2024-03-29"),
                UUID.fromString("589256b4-c5db-4d77-9d50-49c0636a4c52"))).thenReturn(true);

        mockUtilities.when(() -> Utilities.isWeekend(6)).thenReturn(true);

        List<ConsultantTimeDto> mockedTimeItemsList = RegisteredTimeServiceMockedData.createSemesterRegisteredWithOHours();
        List<ConsultantTimeDto> actualResult = registeredTimeService.filterOutIncorrectlyRegisteredTime(mockedTimeItemsList);
        assertEquals(3, actualResult.size());
    }
}
