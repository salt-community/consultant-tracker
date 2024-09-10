package com.example.backend.consultant;

import com.example.backend.ApplicationTestConfig;
import com.example.backend.ObjectConstructor;
import com.example.backend.client.timekeeper.TimekeeperClient;
import com.example.backend.client.timekeeper.dto.TimekeeperUserDto;
import com.example.backend.consultant.dto.ClientsListDto;
import com.example.backend.consultant.dto.ConsultantResponseListDto;
import com.example.backend.consultant.dto.TotalDaysStatisticsDto;
import com.example.backend.meetings_schedule.MeetingsScheduleService;
import com.example.backend.registeredTime.MockedRegisteredTimeService;
import com.example.backend.registeredTime.RegisteredTime;
import com.example.backend.registeredTime.RegisteredTimeService;
import com.example.backend.saltUser.SaltUserService;
import com.example.backend.tag.Tag;
import com.example.backend.timeChunks.TimeChunks;
import com.example.backend.timeChunks.TimeChunksKey;
import com.example.backend.timeChunks.TimeChunksService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = ApplicationTestConfig.class)
class ConsultantServiceTest extends ApplicationTestConfig {
    @MockBean
    TimekeeperClient mockedTkClient;
    @MockBean
    ConsultantRepository mockedConsultantRepo;
    @MockBean
    RegisteredTimeService mockedRegisteredTimeService;
    @MockBean
    TimeChunksService mockedTimeChunksService;
    @MockBean
    MeetingsScheduleService mockedMeetingsScheduleService;
    @MockBean
    SaltUserService mockedSaltUserService;
    @InjectMocks
    ConsultantService consultantService;
    private static Consultant mockedConsultant1;
    private static Consultant mockedConsultant2;
    private static Consultant mockedConsultant3;


    @BeforeEach
    void setUpBeforeEach() {
        consultantService = new ConsultantService(mockedConsultantRepo, mockedTkClient, mockedRegisteredTimeService, mockedTimeChunksService, mockedMeetingsScheduleService,mockedSaltUserService);
        // mock consultants
        mockedConsultant1 = new Consultant(
                UUID.fromString("68c670d6-3038-4fca-95be-2669aaf0b549"),
                "John Doe",
                "john.doe@gmail.com",
                1111L,
                null,
                true,
                "H&M",
                "Sverige",
                null);
        mockedConsultant2 = new Consultant(
                UUID.fromString("0239ceac-5e65-40a6-a949-5492c22b22e3"),
                "John Doe2",
                "john.doe2@gmail.com",
                2222L,
                null,
                true,
                "H&M",
                "Sverige",
                null);
        mockedConsultant3 = new Consultant(
                UUID.fromString("1239cead-5e65-40a6-a949-5492c22b22a4"),
                "John Doe3",
                "john.doe3@gmail.com",
                3333L,
                null,
                true,
                "H&M",
                "Sverige",
                null
                );
    }

    @AfterEach
    void setUpAfterEach() {
        MockedConsultantService.clearList();
    }

    @Test
    void resultShouldNotBeEmptyList() {
        /* ARRANGE */
        List<Consultant> mockedList = List.of(mockedConsultant1, mockedConsultant2);
        Mockito.when(mockedConsultantRepo.findAll()).thenReturn(mockedList);

        /* ACT */
        List<Consultant> consultantList = consultantService.getAllConsultants();

        /* ASSERT */
        assertFalse(consultantList.isEmpty());
    }

    @Test
    void getCountryCodeByConsultantId() {
        /* ARRANGE */
        Mockito.when(mockedConsultantRepo.findCountryById(any(UUID.class))).thenReturn("Sverige");
        String expectedResult = "Sverige";

        /* ACT */
        String result = consultantService.getCountryCodeByConsultantId(UUID.fromString("223152ac-6af6-4a4b-b86b-53c0707f433c"));

        /* ASSERT */
        assertEquals(expectedResult, result);
    }

    @Test
    void shouldReturnConsultantResponseListDto() {
        /* ARRANGE */
        Page<Consultant> pageableConsultantsList = new PageImpl<>(List.of(mockedConsultant1));
        int expectedConsultantsFound = 1;
        List<TimeChunks> timeChunks = new ArrayList<>();
        timeChunks.add(new TimeChunks(new TimeChunksKey(UUID.randomUUID(), LocalDateTime.now()), "Konsult-tid", LocalDateTime.now(), 10, "ABC"));
        Mockito.when(mockedRegisteredTimeService.getAllDaysStatistics(any(UUID.class))).thenReturn(new TotalDaysStatisticsDto(0.0, 0, 0,0,0,0,0, 0.0, 0.0));
        Mockito.when(mockedTimeChunksService.getTimeChunksByConsultant(any(UUID.class))).thenReturn(timeChunks);

        /* ARRANGE FOR HELPER METHOD - getAllConsultantsPageable() */
        Mockito.when(mockedConsultantRepo.findAllByActiveTrueAndFilterByNameAndResponsiblePtAndClientsOrderByFullNameAsc(
                anyString(),
                any(Pageable.class),
                any(List.class),
                any(List.class))).thenReturn(pageableConsultantsList);

        /* ACT */
        ConsultantResponseListDto mockedResult = consultantService.getAllConsultantDtos(0, 8, "mockJohn", List.of("mockPt"), List.of("mockClient"));

        /* ASSERT */
        assertEquals(expectedConsultantsFound, mockedResult.consultants().size());
    }

    @Test
    void shouldReturn2ActiveConsultants() {
        /* ARRANGE */
        Mockito.when(mockedConsultantRepo.findAllByActiveTrue()).thenReturn(List.of(mockedConsultant1, mockedConsultant2));
        int expectedListSize = 2;

        /* ACT */
        int actualListSize = consultantService.getAllActiveConsultants().size();

        /* ASSERT */
        assertEquals(expectedListSize, actualListSize);
    }

    @Test
    @SneakyThrows
    void shouldAddConsultantToList() {
        /* ARRANGE */
        var consultantServiceClass = new ConsultantService(mockedConsultantRepo, mockedTkClient, mockedRegisteredTimeService,mockedTimeChunksService,mockedMeetingsScheduleService, mockedSaltUserService);
        var createConsultantMethod = consultantServiceClass.getClass().getDeclaredMethod("saveConsultant", Consultant.class);
        createConsultantMethod.setAccessible(true);
        var listSizeBefore = MockedConsultantService.mockedGetConsultantsList().size();
        Mockito.when(mockedConsultantRepo.save(any(Consultant.class)))
                .thenReturn(MockedConsultantService.mockedCreateConsultant(mockedConsultant1));

        /* ACT */
        createConsultantMethod.invoke(consultantServiceClass, any(Consultant.class));

        /* ASSERT */
        var listSizeAfter = MockedConsultantService.mockedGetConsultantsList().size();
        int actualResult = listSizeAfter - listSizeBefore;
        assertEquals(1, actualResult);
    }

    @Test
    @SneakyThrows
    void should_UpdateConsultantActiveStatus_ToFalse() {
        /* ARRANGE */
        List<Consultant> mockedList = List.of(mockedConsultant1, mockedConsultant2, mockedConsultant3);
        for (var consultant : mockedList) {
            MockedConsultantService.mockedCreateConsultant(consultant);
        }

        UUID expectedResult = mockedConsultant3.getId();
        mockedConsultant3.setActive(false);

        Mockito.when(mockedConsultantRepo.save(any(Consultant.class)))
                .thenReturn(MockedConsultantService.mockedUpdateConsultant(mockedConsultant3));

        var consultantServiceClass = new ConsultantService(mockedConsultantRepo, mockedTkClient, mockedRegisteredTimeService, mockedTimeChunksService,mockedMeetingsScheduleService, mockedSaltUserService);
        var updateIsActiveForExistingConsultantMethod = consultantServiceClass
                .getClass()
                .getDeclaredMethod("updateExistingConsultant", TimekeeperUserDto.class);
        updateIsActiveForExistingConsultantMethod.setAccessible(true);

        /* ARRANGE FOR HELPER METHOD - getAllConsultants() */
        Mockito.when(mockedConsultantRepo.findAll()).thenReturn(MockedConsultantService.mockedGetConsultantsList());

        /* ACT */
        updateIsActiveForExistingConsultantMethod.invoke(
                consultantServiceClass,
                ObjectConstructor.convertConsultantToTimekeeperUserDto(mockedConsultant3));

        /* ASSERT */
        UUID actualResult = MockedConsultantService.mockedGetConsultantsList()
                .stream()
                .filter(c -> !c.isActive()).toList().getFirst().getId();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @SneakyThrows
    void should_AddNewConsultant_FromTimekeeperUser() {
        /* ARRANGE */
        int listSizeBeforeAdding = MockedConsultantService.mockedGetConsultantsList().size();
        List<TimekeeperUserDto> mockedListTimekeeperUser = ObjectConstructor.getListOfTimekeeperUserDto(1);
        Mockito.when(mockedConsultantRepo.existsByTimekeeperId(anyLong())).thenReturn(false);
        try (MockedStatic<Tag> mockTag = mockStatic(Tag.class)) {
            mockTag.when(() -> Tag.extractCountryTagFromTimekeeperUserDto(any(TimekeeperUserDto.class))).thenReturn("Sverige");
        }
        var consultantServiceClass = new ConsultantService(mockedConsultantRepo, mockedTkClient, mockedRegisteredTimeService, mockedTimeChunksService,mockedMeetingsScheduleService, mockedSaltUserService);
        var updateConsultantTableMethod = consultantServiceClass.getClass().getDeclaredMethod("updateConsultantTable", List.class);
        updateConsultantTableMethod.setAccessible(true);

        /* ARRANGE FOR HELPER METHOD - createConsultant() */
        Mockito.when(mockedConsultantRepo.save(any(Consultant.class)))
                .thenReturn(MockedConsultantService.mockedCreateConsultant(
                        ObjectConstructor.convertTimekeeperUserDtoToConsultant(mockedListTimekeeperUser.getFirst())
                ));

        /* ACT */
        updateConsultantTableMethod.invoke(consultantServiceClass, mockedListTimekeeperUser);

        /* ASSERT */
        int listSizeAfterAdding = MockedConsultantService.mockedGetConsultantsList().size();
        assertEquals(1, listSizeAfterAdding - listSizeBeforeAdding);
    }

    @Test
    @SneakyThrows
    void should_UpdateActiveStatus_ForTimekeeperUser_ToFalse() {
        /* ARRANGE */
        MockedConsultantService.mockedCreateConsultant(mockedConsultant1);
        boolean statusBeforeChange = MockedConsultantService.mockedGetConsultantsList().getFirst().isActive();
        mockedConsultant1.setActive(false);
        List<TimekeeperUserDto> mockedListTimekeeperUser = List.of(
                ObjectConstructor.convertConsultantToTimekeeperUserDto(mockedConsultant1));

        Mockito.when(mockedConsultantRepo.existsByTimekeeperId(anyLong())).thenReturn(true);
        Mockito.when(mockedConsultantRepo.findAll()).thenReturn(MockedConsultantService.mockedGetConsultantsList());
        var consultantServiceClass = new ConsultantService(mockedConsultantRepo, mockedTkClient, mockedRegisteredTimeService, mockedTimeChunksService,mockedMeetingsScheduleService, mockedSaltUserService);
        var updateConsultantTableMethod = consultantServiceClass.getClass().getDeclaredMethod("updateConsultantTable", List.class);
        updateConsultantTableMethod.setAccessible(true);

        /* ARRANGE FOR HELPER METHOD - createConsultant() */
        Mockito.when(mockedConsultantRepo.save(any(Consultant.class)))
                .thenReturn(MockedConsultantService.mockedUpdateConsultant(mockedConsultant1));

        /* ACT */
        updateConsultantTableMethod.invoke(consultantServiceClass, mockedListTimekeeperUser);

        /* ASSERT */
        boolean statusAfterChange = MockedConsultantService.mockedGetConsultantsList().getFirst().isActive();
        assertNotEquals(statusBeforeChange, statusAfterChange);
    }

    @Test
    @SneakyThrows
    void should_NotUpdateActiveStatus_ForTimekeeperUser() {
        /* ARRANGE */
        MockedConsultantService.mockedCreateConsultant(mockedConsultant1);
        boolean statusBeforeChange = MockedConsultantService.mockedGetConsultantsList().getFirst().isActive();
        List<TimekeeperUserDto> mockedListTimekeeperUser = List.of(
                ObjectConstructor.convertConsultantToTimekeeperUserDto(mockedConsultant1));

        Mockito.when(mockedConsultantRepo.existsByTimekeeperId(anyLong())).thenReturn(true);
        var consultantServiceClass = new ConsultantService(mockedConsultantRepo, mockedTkClient, mockedRegisteredTimeService, mockedTimeChunksService,mockedMeetingsScheduleService, mockedSaltUserService);
        var updateConsultantTableMethod = consultantServiceClass.getClass().getDeclaredMethod("updateConsultantTable", List.class);
        updateConsultantTableMethod.setAccessible(true);

        /* ARRANGE FOR HELPER METHOD - updateIsActiveForExistingConsultant() */
        Mockito.when(mockedConsultantRepo.findAll()).thenReturn(MockedConsultantService.mockedGetConsultantsList());
        Mockito.when(mockedConsultantRepo.save(any(Consultant.class)))
                .thenReturn(MockedConsultantService.mockedUpdateConsultant(mockedConsultant1));

        /* ACT */
        updateConsultantTableMethod.invoke(consultantServiceClass, mockedListTimekeeperUser);

        /* ASSERT */
        boolean statusAfterChange = MockedConsultantService.mockedGetConsultantsList().getFirst().isActive();
        assertEquals(statusBeforeChange, statusAfterChange);
    }

    @Test
    @SneakyThrows
    void should_AddNewConsultantAnd_Save3KonsultTidDays_With8HoursEach_FetchDataFromTimekeeper() {
        /* ARRANGE */
        List<TimekeeperUserDto> mockedTkList = ObjectConstructor.getListOfTimekeeperUserDto(1);
        Mockito.when(mockedTkClient.getUsers()).thenReturn(mockedTkList);

        /* ARRANGE FOR HELPER METHOD - updateConsultantTable() */
        Mockito.when(mockedConsultantRepo.existsByTimekeeperId(anyLong())).thenReturn(true);
        try (MockedStatic<Tag> mockTag = mockStatic(Tag.class)) {
            mockTag.when(() -> Tag.extractCountryTagFromTimekeeperUserDto(any(TimekeeperUserDto.class))).thenReturn("Sverige");
        }
        int consultantListSizeBefore = MockedConsultantService.mockedGetConsultantsList().size();

        /* ARRANGE FOR HELPER TO THE HELPER METHOD - createConsultant() */
        Mockito.when(mockedConsultantRepo.save(any(Consultant.class)))
                .thenReturn(MockedConsultantService.mockedCreateConsultant(ObjectConstructor.convertTimekeeperUserDtoToConsultant(mockedTkList.getFirst())));

        /* ARRANGE FOR HELPER METHOD - registeredTimeService.fetchAndSaveTimeRegisteredByConsultant() */
        doAnswer((Answer<Void>) invocation -> {
            MockedRegisteredTimeService.mockedFetchAndSaveTimeRegisteredByConsultant();
            return null;
        }).when(mockedRegisteredTimeService).fetchAndSaveTimeRegisteredByConsultantDB();

        /* ARRANGE FOR HELPER METHOD - fillClientAndResponsiblePt() */
        List<Consultant> activeConsultants = MockedConsultantService.mockedGetConsultantsList();
        Mockito.when(mockedConsultantRepo.save(any(Consultant.class)))
                .thenReturn(MockedConsultantService.mockedUpdateConsultant(activeConsultants.getFirst()));

        /* ARRANGE FOR HELPER TO THE HELPER METHOD - getAllActiveConsultants() */
        Mockito.when(mockedConsultantRepo.findAllByActiveTrue()).thenReturn(activeConsultants);
        Mockito.when(mockedRegisteredTimeService.getCurrentClient(any(UUID.class))).thenReturn("H&M");

        /* ACT */
        consultantService.fetchDataFromTimekeeper();

        /* ASSERT */
        int expectedDifferenceInListSize = 1;
        int consultantListSizeAfter = MockedConsultantService.mockedGetConsultantsList().size();
        List<RegisteredTime> registeredTimeList = MockedRegisteredTimeService.registeredTimeList;
        int expectedNumberOfKonsultTidEntries = 3;
        double expectedSumOfHoursRegistered = 24.0;
        int actualNumberOfKonsultTidEntries = 0;
        double actualSumOfHoursRegistered = 0.0;

        for (var time : registeredTimeList) {
            actualSumOfHoursRegistered += time.getTotalHours();
            if (time.getType().equalsIgnoreCase("Konsult-Tid")) {
                actualNumberOfKonsultTidEntries++;
            }
        }
        assertEquals(expectedDifferenceInListSize, consultantListSizeAfter - consultantListSizeBefore);
        assertEquals(3, registeredTimeList.size());
        assertEquals(expectedNumberOfKonsultTidEntries, actualNumberOfKonsultTidEntries);
        assertEquals(expectedSumOfHoursRegistered, actualSumOfHoursRegistered);
    }

    @Test
    void shouldReturnAList_Of1Client() {
        // mock registeredTimeService.getClientsByConsultantId(consultantId)
        Mockito.when(mockedRegisteredTimeService.getClientsByConsultantId(any(UUID.class)))
                .thenReturn(List.of("H&M"));
        List<ClientsListDto> expectedResult = List.of(new ClientsListDto("H&M", LocalDate.now(), LocalDate.now()));
        List<ClientsListDto> actualResult = consultantService.getClientListByConsultantId(UUID.randomUUID());
        assertEquals(expectedResult.size(), actualResult.size());
    }

    @Test
    void shouldReturnListOf2Clients() {
        String clientA = "client A";
        String clientB = "client B";
        UUID randomUUID = UUID.randomUUID();
        LocalDate startDateClientA = LocalDate.parse("2024-02-05");
        LocalDate endDateClientA = LocalDate.parse("2024-03-05");
        LocalDate startDateClientB = LocalDate.parse("2024-03-06");
        LocalDate endDateClientB = LocalDate.parse("2024-04-05");
        Mockito.when(mockedRegisteredTimeService.getClientsByConsultantId(randomUUID))
                .thenReturn(List.of(clientA, clientB));
        Mockito.when(mockedRegisteredTimeService.getStartDateByClientAndConsultantId(clientA, randomUUID))
                .thenReturn(startDateClientA);
        Mockito.when(mockedRegisteredTimeService.getStartDateByClientAndConsultantId(clientB, randomUUID))
                .thenReturn(startDateClientB);
        Mockito.when(mockedRegisteredTimeService.getEndDateByClientAndConsultantId(clientA, randomUUID))
                .thenReturn(endDateClientA);
        Mockito.when(mockedRegisteredTimeService.getEndDateByClientAndConsultantId(clientB, randomUUID))
                .thenReturn(endDateClientB);
        List<ClientsListDto> expectedResult = List.of(
                new ClientsListDto(clientA, startDateClientA, endDateClientA),
                new ClientsListDto(clientB, startDateClientB, endDateClientB));
        List<ClientsListDto> actualResult = consultantService.getClientListByConsultantId(randomUUID);
        assertEquals(expectedResult.size(), actualResult.size());
    }

    @Test
    void should_ExcludePGP_And_ReturnListOf1Client() {
        String pgp = "PGP";
        String clientB = "client B";
        UUID randomUUID = UUID.randomUUID();
        LocalDate startDateClientA = LocalDate.parse("2024-02-05");
        LocalDate endDateClientA = LocalDate.parse("2024-03-05");
        LocalDate startDateClientB = LocalDate.parse("2024-03-06");
        LocalDate endDateClientB = LocalDate.parse("2024-04-05");
        Mockito.when(mockedRegisteredTimeService.getClientsByConsultantId(randomUUID))
                .thenReturn(List.of(pgp, clientB));
        Mockito.when(mockedRegisteredTimeService.getStartDateByClientAndConsultantId(pgp, randomUUID))
                .thenReturn(startDateClientA);
        Mockito.when(mockedRegisteredTimeService.getStartDateByClientAndConsultantId(clientB, randomUUID))
                .thenReturn(startDateClientB);
        Mockito.when(mockedRegisteredTimeService.getEndDateByClientAndConsultantId(pgp, randomUUID))
                .thenReturn(endDateClientA);
        Mockito.when(mockedRegisteredTimeService.getEndDateByClientAndConsultantId(clientB, randomUUID))
                .thenReturn(endDateClientB);
        List<ClientsListDto> expectedResult = List.of(
                new ClientsListDto(clientB, startDateClientB, endDateClientB));
        List<ClientsListDto> actualResult = consultantService.getClientListByConsultantId(randomUUID);
        assertEquals(expectedResult.size(), actualResult.size());
    }

    @Test
    void should_ExcludeUpskilling_And_ReturnListOf1Client() {
        String upskilling = "Upskilling";
        String clientB = "client B";
        UUID randomUUID = UUID.randomUUID();
        LocalDate startDateClientA = LocalDate.parse("2024-02-05");
        LocalDate endDateClientA = LocalDate.parse("2024-03-05");
        LocalDate startDateClientB = LocalDate.parse("2024-03-06");
        LocalDate endDateClientB = LocalDate.parse("2024-04-05");
        Mockito.when(mockedRegisteredTimeService.getClientsByConsultantId(randomUUID))
                .thenReturn(List.of(upskilling, clientB));
        Mockito.when(mockedRegisteredTimeService.getStartDateByClientAndConsultantId(upskilling, randomUUID))
                .thenReturn(startDateClientA);
        Mockito.when(mockedRegisteredTimeService.getStartDateByClientAndConsultantId(clientB, randomUUID))
                .thenReturn(startDateClientB);
        Mockito.when(mockedRegisteredTimeService.getEndDateByClientAndConsultantId(upskilling, randomUUID))
                .thenReturn(endDateClientA);
        Mockito.when(mockedRegisteredTimeService.getEndDateByClientAndConsultantId(clientB, randomUUID))
                .thenReturn(endDateClientB);
        List<ClientsListDto> expectedResult = List.of(
                new ClientsListDto(clientB, startDateClientB, endDateClientB));
        List<ClientsListDto> actualResult = consultantService.getClientListByConsultantId(randomUUID);
        assertEquals(expectedResult.size(), actualResult.size());
    }
}