package com.example.backend.consultant;

import com.example.backend.ApplicationTestConfig;
import com.example.backend.client.timekeeper.TimekeeperClient;
import com.example.backend.client.timekeeper.dto.TimekeeperUserDto;
import com.example.backend.consultant.dto.ConsultantResponseListDto;
import com.example.backend.registeredTime.MockedRegisteredTimeService;
import com.example.backend.registeredTime.RegisteredTimeService;
import com.example.backend.tag.Tag;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;

import java.lang.reflect.InvocationTargetException;
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
    @InjectMocks
    ConsultantService consultantService;
    private MockedStatic<Tag> mockTag;
    private static Consultant mockedConsultant1;
    private static Consultant mockedConsultant2;
    private static Consultant mockedConsultant3;
    @Autowired
    private TimekeeperClient timekeeperClient;

    @BeforeAll
    static void setUp() {
        // mock consultants
        mockedConsultant1 = new Consultant(
                UUID.fromString("68c670d6-3038-4fca-95be-2669aaf0b549"),
                "John Doe",
                "john.doe@gmail.com",
                null,
                1111L,
                "Jane Doe",
                "H&M",
                "Sverige",
                true);
        mockedConsultant2 = new Consultant(
                UUID.fromString("0239ceac-5e65-40a6-a949-5492c22b22e3"),
                "John Doe2",
                "john.doe2@gmail.com",
                null,
                2222L,
                "Jane Doe2",
                "H&M",
                "Sverige",
                true);
        mockedConsultant3 = new Consultant(
                UUID.fromString("1239cead-5e65-40a6-a949-5492c22b22a4"),
                "John Doe3",
                "john.doe3@gmail.com",
                null,
                3333L,
                "Jane Doe3",
                "H&M",
                "Sverige",
                true);
    }

    @Test
    void resultShouldNotBeEmptyList() {
        List<Consultant> mockedList = List.of(mockedConsultant1, mockedConsultant2);
        Mockito.when(mockedConsultantRepo.findAll()).thenReturn(mockedList);
        List<Consultant> consultantList = consultantService.getAllConsultants();
        System.out.println("consultantList = " + consultantList);
        assertFalse(consultantList.isEmpty());
    }

    @Test
    void getCountryCodeByConsultantId() {
        Mockito.when(mockedConsultantRepo.findCountryById(any(UUID.class))).thenReturn("Sverige");
        String result = consultantService.getCountryCodeByConsultantId(UUID.fromString("223152ac-6af6-4a4b-b86b-53c0707f433c"));
        assertEquals("Sverige", result);
    }

    @Test
    void shouldReturnConsultantResponseListDto() {
        Page<Consultant> pageableConsultantsList = new PageImpl<>(List.of(mockedConsultant1));
        Mockito.when(mockedConsultantRepo.findAllByActiveTrueAndFilterByName(anyString(), any(Pageable.class))).thenReturn(pageableConsultantsList);
        Mockito.when(mockedRegisteredTimeService
                .getConsultantTimelineItems(any(Consultant.class))).thenReturn(MockedRegisteredTimeService.getConsultantTimelineItemsMocked(mockedConsultant1));
        ConsultantResponseListDto mockedResult = consultantService.getAllConsultantDtos(0, 8, "mockJohn", "mockPt", "mockClient");
        int expectedConsultantsFound = 1;
        assertEquals(expectedConsultantsFound, mockedResult.consultants().size());
    }

    @Test
    void shouldReturn2ActiveConsultants() {
        Mockito.when(mockedConsultantRepo.findAllByActiveTrue()).thenReturn(List.of(mockedConsultant1, mockedConsultant2));
        int expectedListSize = 2;
        int actualListSize = consultantService.getAllActiveConsultants().size();
        assertEquals(expectedListSize, actualListSize);
    }

    @Test
    @SneakyThrows
    void shouldReturn1ConsultantAdded() {
        // testing updateConsultantTable(List<TimekeeperUserDto> timekeeperUserDto)

        TimekeeperUserDto mockedUserToAdd = new TimekeeperUserDto(
                "mock First Name",
                "mock Last Name",
                "mock email",
                null,
                new ArrayList<>(),
                300L,
                true,
                "mock client",
                "mock responsiblePT",
                true
        );
        List<TimekeeperUserDto> mockedList = List.of(mockedUserToAdd);
        var consultantServiceClass = ConsultantService.class;
//        var createConsultantMethod = consultantServiceClass.getDeclaredMethod("createConsultant", Consultant.class);
//        createConsultantMethod.setAccessible(true);
        var updateConsultantTableMethod = consultantServiceClass.getDeclaredMethod("updateConsultantTable", List.class);
        updateConsultantTableMethod.setAccessible(true);
        Mockito.when(mockedConsultantRepo.existsByTimekeeperId(anyLong())).thenReturn(false);
//        Mockito.when(mockedConsultantRepo.findCountryById(any(UUID.class))).thenReturn("Sverige");
//        Mockito.when(mockedConsultantRepo.save(any(Consultant.class))).then(MockedConsultantService.mockedCreateConsultant(mockedConsultant1));
        mockTag = mockStatic(Tag.class);
        mockTag.when(() -> Tag.extractCountryTagFromTimekeeperUserDto(any(TimekeeperUserDto.class))).thenReturn("Sverige");

//        doAnswer((Answer<Void>) invocationOnMock -> {
//            MockedConsultantService.mockedCreateConsultant(mockedConsultant1);
//            return null;
//        }).when(createConsultantMethod.invoke(consultantServiceClass.getDeclaredConstructor().newInstance(), any(Consultant.class))).notify();

        doAnswer((Answer<Void>) invocationOnMock -> {
            MockedConsultantService.mockedCreateConsultant(mockedConsultant1);
            return null;
        }).when(mockedConsultantRepo).save(any(Consultant.class));

        int listSizeBeforeAdding = MockedConsultantService.mockedGetConsultantsList().size();
        updateConsultantTableMethod.invoke(consultantServiceClass.getDeclaredConstructor().newInstance(), mockedList);
        int listSizeAfterAdding = MockedConsultantService.mockedGetConsultantsList().size();
        assertEquals(1, listSizeAfterAdding - listSizeBeforeAdding);
    }

    @Test
    void shouldAddConsultantToList() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        var consultantServiceClass = new ConsultantService(mockedConsultantRepo, mockedTkClient, mockedRegisteredTimeService);
        var createConsultantMethod = consultantServiceClass.getClass().getDeclaredMethod("createConsultant", Consultant.class);
        createConsultantMethod.setAccessible(true);
        var listSizeBefore = MockedConsultantService.mockedGetConsultantsList().size();
        Mockito.when(mockedConsultantRepo.save(any(Consultant.class)))
                .thenReturn(MockedConsultantService.mockedCreateConsultant(mockedConsultant1));
        createConsultantMethod.invoke(consultantServiceClass, any(Consultant.class));
        var listSizeAfter = MockedConsultantService.mockedGetConsultantsList().size();
        int actualResult = listSizeAfter - listSizeBefore;
        assertEquals(1, actualResult);
    }

    @Test
    void shouldUpdateConsultantActiveStatusToFalse() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //private void updateIsActiveForExistingConsultant(TimekeeperUserDto tkUser)
        /* ARRANGE */
        List<Consultant> mockedList = List.of(mockedConsultant1, mockedConsultant2, mockedConsultant3);
        for (var consultant: mockedList) {
            MockedConsultantService.mockedCreateConsultant(consultant);
        }
        Mockito.when(mockedConsultantRepo.findAll()).thenReturn(MockedConsultantService.mockedGetConsultantsList());
        mockedConsultant3.setActive(true);

        UUID expectedResult = mockedConsultant3.getId();

        Mockito.when(mockedConsultantRepo.save(any(Consultant.class)))
                .thenReturn(MockedConsultantService.mockedCreateConsultant(mockedConsultant3));

        var consultantServiceClass = new ConsultantService(mockedConsultantRepo, mockedTkClient, mockedRegisteredTimeService);
        var updateIsActiveForExistingConsultantMethod = consultantServiceClass
                .getClass()
                .getDeclaredMethod("updateIsActiveForExistingConsultant", TimekeeperUserDto.class);
        updateIsActiveForExistingConsultantMethod.setAccessible(true);

        /* ACT */
        updateIsActiveForExistingConsultantMethod.invoke(
                consultantServiceClass,
                new TimekeeperUserDto(
                        "John",
                        "Doe3",
                        mockedConsultant3.getEmail(),
                        mockedConsultant3.getPhoneNumber(),
                        null,
                        mockedConsultant3.getTimekeeperId(),
                        false,
                        mockedConsultant3.getClient(),
                        mockedConsultant3.getResponsiblePT(),
                        true
                ));

        UUID actualResult = MockedConsultantService.mockedGetConsultantsList()
                .stream()
                .filter(c -> !c.isActive()).toList().get(0).getId();

        /* ASSERT */
        assertEquals(expectedResult, actualResult);
    }
    // the way the methods are set up this test doesn't make sense in unit testing
//    @Test
//    void shouldAddNewConsultant() {
//        Tag mockTag = new Tag();
//        mockTag.setName("mock-tag");
//        List<TimekeeperUserDto> mockedTkUserList = List.of(
//                new TimekeeperUserDto(
//                        "MockFirstName",
//                        "MockLastName",
//                        "mockEmail",
//                        "mockPhone",
//                        List.of(mockTag),
//                        1000L,
//                        true,
//                        "mockClient",
//                        "mockResponsiblePT",
//                        true
//                )
//        );
//        Mockito.when(tkClient.getUsers()).thenReturn(mockedTkUserList);
//        int totalConsultantsBefore = consultantService.getAllConsultants().size();
//        consultantService.fetchDataFromTimekeeper();
//        int totalConsultantsAfter = consultantService.getAllConsultants().size();
//        System.out.println("2nd test: totalConsultantsAfter = " + totalConsultantsAfter);
//        int actualResult = totalConsultantsAfter - totalConsultantsBefore;
//        assertEquals(1, actualResult);
//    }
}