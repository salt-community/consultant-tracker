package com.example.backend.registeredTime;

import com.example.backend.ApplicationTestConfig;
import com.example.backend.client.timekeeper.TimekeeperClient;
import com.example.backend.consultant.ConsultantService;
import com.example.backend.consultant.dto.ConsultantTimeDto;
import com.example.backend.redDay.RedDayService;
import com.example.backend.registeredTime.dto.RegisteredTimeResponseDto;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.example.backend.client.timekeeper.Activity.*;
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
    private RedDayService redDaysService;
    private RegisteredTimeService registeredTimeService;
    private final List<UUID> listOfConsultantIds = new ArrayList<>(List.of(UUID.fromString("68c670d6-3038-4fca-95be-2669aaf0b549"),
            UUID.fromString("0239ceac-5e65-40a6-a949-5492c22b22e3")));

    @BeforeEach
    public void setUp() {
        this.registeredTimeService = new RegisteredTimeService(
                registeredTimeRepository,
                consultantService,
                timekeeperClient,
                redDaysService);
    }

    @Test
    @DisplayName("getTimeByConsultantId")
    public void shouldReturnTwoTimeItems() {
        Mockito.when(registeredTimeRepository.findAllById_ConsultantIdOrderById_StartDateAsc(
                        listOfConsultantIds.get(0)))
                .thenReturn(RegisteredTimeServiceMockedData.generateMockedRegisteredTimeData());
        List<RegisteredTime> actualResult = registeredTimeService.getTimeByConsultantId(
                listOfConsultantIds.get(0));
        assertEquals(2, actualResult.size());
    }

    @Test
    @DisplayName("getCurrentClient")
    public void shouldReturnPGPWhenConsultantDidNotWorkYet() {
        Mockito.when(registeredTimeRepository.findFirstById_ConsultantIdAndTypeIsOrderByEndDateDesc(
                        listOfConsultantIds.get(0), CONSULTANCY_TIME.activity))
                .thenReturn(null);
        String actualResult = registeredTimeService.getCurrentClient(listOfConsultantIds.get(0));
        assertEquals(actualResult, PGP.activity);
    }

    @Test
    public void shouldReturnCurrentClientWhenConsultantWorked() {
        Mockito.when(registeredTimeRepository.findFirstById_ConsultantIdAndTypeIsOrderByEndDateDesc(
                        listOfConsultantIds.get(0), CONSULTANCY_TIME.activity))
                .thenReturn(RegisteredTimeServiceMockedData.createMockedLasWorkTimeRegistered());
        String actualResult = registeredTimeService.getCurrentClient(listOfConsultantIds.get(0));
        assertEquals(actualResult, "H&M");
    }

    @Test
    public void shouldReturnTotalWorkedHours() {
        Mockito.lenient().when(registeredTimeRepository.getSumOfTotalHoursByConsultantIdAndType(
                        listOfConsultantIds.get(0),
                        CONSULTANCY_TIME.activity))
                .thenReturn(Optional.of(150D));
        Mockito.lenient().when(registeredTimeRepository.getSumOfTotalHoursByConsultantIdAndType(
                        listOfConsultantIds.get(0),
                        OWN_ADMINISTRATION.activity))
                .thenReturn(Optional.of(100D));
        double actualResult = registeredTimeService.countTotalWorkedHours(listOfConsultantIds.get(0));
        assertEquals(250D, actualResult);
    }

    @Test
    public void shouldReturnNoItemsWhenListIncludesOnlyWronglyRegisteredTime(){
        List<ConsultantTimeDto> mockedTimeItemsList = RegisteredTimeServiceMockedData.createAllWronglyRegisteredTimeMockedData();
        List<ConsultantTimeDto> actualResult = registeredTimeService.filterOutIncorrectlyRegisteredTimeDB(mockedTimeItemsList);
        assertEquals(0, actualResult.size());
    }

    @Test
    public void shouldReturnThreeTimeItemsWhenListIncludesIncorrectlyRegisteredItemButTypeSemester() {
        List<ConsultantTimeDto> mockedTimeItemsList = RegisteredTimeServiceMockedData.createSomeWronglyRegisteredTimeMockedData();
        List<ConsultantTimeDto> actualResult = registeredTimeService.filterOutIncorrectlyRegisteredTimeDB(mockedTimeItemsList);
        assertEquals(2, actualResult.size());
    }

    @Test
    public void shouldGetAllConsultantsTimeItems() {
        Mockito.lenient().when(consultantService.getAllConsultants()).thenReturn(
                RegisteredTimeServiceMockedData.createMockedListOfConsultants());
        Mockito.lenient().when(registeredTimeRepository.findAllById_ConsultantIdOrderById_StartDateAsc(
                        listOfConsultantIds.get(0)))
                .thenReturn(RegisteredTimeServiceMockedData.generateMockedRegisteredTimeData());
        Mockito.lenient().when(registeredTimeRepository.findAllById_ConsultantIdOrderById_StartDateAsc(
                        listOfConsultantIds.get(1)))
                .thenReturn(RegisteredTimeServiceMockedData.generateMockedRegisteredTimeData());
        List<ConsultantTimeDto> expectedResult = RegisteredTimeServiceMockedData.generateMockedExpectedResult();
        List<ConsultantTimeDto> actualResult = registeredTimeService.getAllConsultantsTimeItems();
        assertEquals(expectedResult.size(), actualResult.size());
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnNullWhenRegisteredTimeListIsEmpty() {
        Mockito.lenient().when(registeredTimeRepository.findAllById_ConsultantIdOrderById_StartDateAsc(
                        listOfConsultantIds.get(0)))
                .thenReturn(new ArrayList<>());
        List<RegisteredTimeResponseDto> actualResult = registeredTimeService.getGroupedConsultantsRegisteredTimeItems(listOfConsultantIds.get(0));
        assertNull(actualResult);
    }

    @Test
    public void shouldReturnGroupedConsultantsRegisteredTimeItems() {
        DateTimeFormatter formatterSeconds = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Mockito.lenient().when(registeredTimeRepository.findAllById_ConsultantIdOrderById_StartDateAsc(
                        listOfConsultantIds.get(0)))
                .thenReturn(RegisteredTimeServiceMockedData.createMockedRegisteredTimeList());
        Mockito.lenient().when(registeredTimeRepository.findFirstById_ConsultantIdOrderByEndDateDesc(
                        listOfConsultantIds.get(0)))
                .thenReturn(new RegisteredTime(new RegisteredTimeKey(
                        UUID.fromString("68c670d6-3038-4fca-95be-2669aaf0b549"),
                        LocalDateTime.parse("2024-06-28 00:00:00", formatterSeconds)),
                        "Konsult-tid",
                        LocalDateTime.parse("2024-06-28 23:59:59", formatterSeconds),
                        8D,
                        "Swedbank"));
        Mockito.lenient().when(consultantService.getCountryCodeByConsultantId(
                        UUID.fromString("68c670d6-3038-4fca-95be-2669aaf0b549")))
                .thenReturn("SE");
        Mockito.lenient().when(redDaysService.checkRedDaysOrWeekend(3L, LocalDate.parse("2024-05-24"),
                        UUID.fromString("68c670d6-3038-4fca-95be-2669aaf0b549"),"single check"))
                .thenReturn(2);
        Mockito.lenient().when(redDaysService.checkRedDaysOrWeekend(32L, LocalDate.parse("2024-05-27"),
                        UUID.fromString("68c670d6-3038-4fca-95be-2669aaf0b549"),"multiple check"))
                .thenReturn(10);
        Mockito.lenient().when(redDaysService.checkRedDaysOrWeekend(3L, LocalDate.parse("2024-06-28"),
                        UUID.fromString("68c670d6-3038-4fca-95be-2669aaf0b549"),"single check"))
                .thenReturn(2);
        Mockito.lenient().when(redDaysService.checkRedDaysOrWeekend(29L, LocalDate.parse("2024-07-03"),
                        UUID.fromString("68c670d6-3038-4fca-95be-2669aaf0b549"),"multiple check"))
                .thenReturn(8);
        Mockito.lenient().when(redDaysService.checkRedDaysOrWeekend(3L, LocalDate.parse("2024-08-02"),
                        UUID.fromString("68c670d6-3038-4fca-95be-2669aaf0b549"),"single check"))
                .thenReturn(2);
        List<RegisteredTimeResponseDto> actualResult = registeredTimeService.getGroupedConsultantsRegisteredTimeItems(
                UUID.fromString("68c670d6-3038-4fca-95be-2669aaf0b549"));
        actualResult.forEach(el->System.out.println("el= " + el));
        List<RegisteredTimeResponseDto> expectedResult = RegisteredTimeServiceMockedData.createGroupedRegisteredTimeDtoResponse();
        assertEquals(expectedResult.getFirst().startDate(), actualResult.getFirst().startDate());
        assertEquals(expectedResult.getFirst().endDate(), actualResult.getFirst().endDate());
        assertEquals(expectedResult.getFirst().type(), actualResult.getFirst().type());
        assertEquals(expectedResult.getFirst().days(), actualResult.getFirst().days());
        assertEquals(expectedResult.getFirst().projectName(), actualResult.getFirst().projectName());
    }
}