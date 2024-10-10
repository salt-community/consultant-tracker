package salt.consultanttracker.api.meetings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import salt.consultanttracker.api.ApplicationTestConfig;
import salt.consultanttracker.api.consultant.ConsultantService;
import salt.consultanttracker.api.registeredtime.RegisteredTimeService;
import salt.consultanttracker.api.timechunks.TimeChunks;
import salt.consultanttracker.api.timechunks.TimeChunksKey;
import salt.consultanttracker.api.timechunks.TimeChunksService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = ApplicationTestConfig.class)
class MeetingsScheduleServiceTest {

    MeetingsScheduleService meetingsScheduleService;
    @MockBean
    ConsultantService consultantService;
    @MockBean
    TimeChunksService timeChunksService;
    @MockBean
    RegisteredTimeService registeredTimeService;
    @MockBean
    MeetingsScheduleRepository meetingsScheduleRepository;

    @BeforeEach
    public void setUp() {
        this.meetingsScheduleService = new MeetingsScheduleService(
                consultantService,
                timeChunksService,
                registeredTimeService,
                meetingsScheduleRepository);
    }

    @Test
    void shouldReturn_2024_03_29_ForStartDate_2024_03_01_WithOnlyKonsultTid() {
        /* ARRANGE */
        List<TimeChunks> mockedTimeChunks = List.of(
                new TimeChunks(new TimeChunksKey(UUID.randomUUID(), LocalDateTime.of(2024, 03, 01, 0, 0, 0)),
                        "Konsult-Tid",
                        LocalDateTime.of(2024, 03, 01, 23, 59, 59),
                        20, "Client A"));
        LocalDate expectedResult = LocalDate.of(2024, 3, 29);
        /* ACT */
        LocalDate actualResult = meetingsScheduleService.getFirstMeetingDate(mockedTimeChunks);
        System.out.println("actualResult = " + actualResult);
        /* ASSERT */
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void shouldReturn_2023_09_24_ForStartDate_2023_08_21_WithAbsence() {
        /* ARRANGE */
        List<TimeChunks> mockedTimeChunks = List.of(
                new TimeChunks(new TimeChunksKey(UUID.randomUUID(), LocalDateTime.of(2023, 8, 21, 0, 0, 0)),
                        "Konsult-Tid",
                        LocalDateTime.of(2023, 8, 27, 23, 59, 59),
                        5, "Client A"),
                new TimeChunks(new TimeChunksKey(UUID.randomUUID(), LocalDateTime.of(2023, 8, 28, 0, 0, 0)),
                        "Sjuk",
                        LocalDateTime.of(2023, 9, 6, 23, 59, 59),
                        6, "Client A"),
                new TimeChunks(new TimeChunksKey(UUID.randomUUID(), LocalDateTime.of(2023, 8, 30, 0, 0, 0)),
                        "Konsult-Tid",
                        LocalDateTime.of(2024, 6, 2, 23, 59, 59),
                        191, "Client A"));
        LocalDate expectedResult = LocalDate.of(2023, 9, 24);
        /* ACT */
        LocalDate actualResult = meetingsScheduleService.getFirstMeetingDate(mockedTimeChunks);
        System.out.println("actualResult = " + actualResult);
        /* ASSERT */
        assertEquals(expectedResult, actualResult);
    }
}