package salt.consultanttracker.api.registeredtime;

import salt.consultanttracker.api.ApplicationTestConfig;
import salt.consultanttracker.api.client.timekeeper.TimekeeperClient;
import salt.consultanttracker.api.consultant.ConsultantService;
import salt.consultanttracker.api.consultant.dto.ConsultantTimeDto;
import salt.consultanttracker.api.reddays.RedDayService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static salt.consultanttracker.api.client.timekeeper.Activity.*;
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
        Mockito.lenient().when(registeredTimeRepository.findFirstById_ConsultantIdAndTypeIsOrderByEndDateDesc(
                        listOfConsultantIds.get(0), CONSULTANCY_TIME.activity))
                .thenReturn(null);
        String actualResult = registeredTimeService.getCurrentClient(listOfConsultantIds.get(0));
        assertEquals(actualResult, PGP.activity);
    }

    @Test
    public void shouldReturnCurrentClientWhenConsultantWorked() {
        Mockito.when(registeredTimeRepository.findFirstById_ConsultantIdOrderByEndDateDesc(
                        listOfConsultantIds.get(0))).thenReturn(null);
        String expectedResult = PGP.activity;
        String actualResult = registeredTimeService.getCurrentClient(listOfConsultantIds.get(0));
        assertEquals(expectedResult, actualResult);
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
        List<RegisteredTime> mockedTimeItemsList = RegisteredTimeServiceMockedData.createAllWronglyRegisteredTimeMockedData();
        List<RegisteredTime> actualResult = registeredTimeService.filterOutIncorrectlyRegisteredTimeDB(mockedTimeItemsList);
        assertEquals(0, actualResult.size());
    }

    @Test
    public void shouldReturnThreeTimeItemsWhenListIncludesIncorrectlyRegisteredItemButTypeSemester() {
        List<RegisteredTime> mockedTimeItemsList = RegisteredTimeServiceMockedData.createSomeWronglyRegisteredTimeMockedData();
        List<RegisteredTime> actualResult = registeredTimeService.filterOutIncorrectlyRegisteredTimeDB(mockedTimeItemsList);
        assertEquals(2, actualResult.size());
    }
}