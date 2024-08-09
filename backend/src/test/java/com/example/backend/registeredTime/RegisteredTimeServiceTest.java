package com.example.backend.registeredTime;

import com.example.backend.ApplicationTestConfig;
import com.example.backend.client.timekeeper.TimekeeperClient;
import com.example.backend.consultant.ConsultantService;
import com.example.backend.redDays.RedDaysService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.example.backend.client.timekeeper.Activity.CONSULTANCY_TIME;
import static com.example.backend.client.timekeeper.Activity.OWN_ADMINISTRATION;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = ApplicationTestConfig.class)
class RegisteredTimeServiceTest {
    @Mock
    private RegisteredTimeRepository registeredTimeRepository;
    @Mock
    private ConsultantService consultantService;
    @Mock
    private TimekeeperClient timekeeperClient;
    @Mock
    private RedDaysService redDaysService;
    private RegisteredTimeService registeredTimeService;

    @BeforeEach
    public void setUp() {
        this.registeredTimeService = new RegisteredTimeService(
                registeredTimeRepository,
                consultantService,
                timekeeperClient,
                redDaysService);
    }

    @Test
    public void shouldReturnTwoTimeItems() {
        Mockito.when(registeredTimeRepository.findAllById_ConsultantIdOrderById_StartDateAsc(
                        UUID.fromString("68c670d6-3038-4fca-95be-2669aaf0b549")))
                .thenReturn(RegisteredTimeServiceMockedData.generateMockedRegisteredTimeData());
        List<RegisteredTime> actualResult = registeredTimeService.getTimeByConsultantId(
                UUID.fromString("68c670d6-3038-4fca-95be-2669aaf0b549"));
        assertEquals(2, actualResult.size());
    }

    @Test
    public void shouldReturnTotalWorkedHours() {
        Mockito.lenient().when(registeredTimeRepository.getSumOfTotalHoursByConsultantIdAndProjectName(
                        UUID.fromString("68c670d6-3038-4fca-95be-2669aaf0b549"),
                        CONSULTANCY_TIME.activity))
                .thenReturn(Optional.of(150D));
        Mockito.lenient().when(registeredTimeRepository.getSumOfTotalHoursByConsultantIdAndProjectName(
                        UUID.fromString("68c670d6-3038-4fca-95be-2669aaf0b549"),
                        OWN_ADMINISTRATION.activity))
                .thenReturn(Optional.of(100D));
        double actualResult = registeredTimeService.countTotalWorkedHours(UUID.fromString("68c670d6-3038-4fca-95be-2669aaf0b549"));
        assertEquals(250D, actualResult);
    }

}