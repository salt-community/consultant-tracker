package com.example.backend.registeredTime;

import com.example.backend.ApplicationTestConfig;
import com.example.backend.client.timekeeper.TimekeeperClient;
import com.example.backend.consultant.ConsultantService;
import com.example.backend.redDays.RedDaysService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
                .thenReturn(RegisteredTimeMockedData.generateMockedRegisteredTimeData());
        List<RegisteredTime> actualResult = registeredTimeService.getTimeByConsultantId(
                UUID.fromString("68c670d6-3038-4fca-95be-2669aaf0b549"));
        assertEquals(actualResult.size(), 2);

    }

}