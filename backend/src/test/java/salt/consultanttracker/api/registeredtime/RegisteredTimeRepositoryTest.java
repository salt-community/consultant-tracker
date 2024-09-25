package salt.consultanttracker.api.registeredtime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RegisteredTimeRepositoryTest {
    @Autowired
    RegisteredTimeRepository registeredTimeRepository;
    RegisteredTime registeredTime;
    UUID mockedId = UUID.fromString("01438d07-91cb-4f4d-adab-aa050462779e");
    RegisteredTimeKey id;
    private final DateTimeFormatter formatterSeconds = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @BeforeEach
    public void setUp() {
        registeredTime = new RegisteredTime(new RegisteredTimeKey(mockedId, LocalDateTime.parse("2024-02-05 00:00:00", formatterSeconds)),
                "Konsult-tid",
                LocalDateTime.parse("2024-02-05 23:59:59", formatterSeconds),
                8D,
                "H&M");
        RegisteredTime savedMockedData = registeredTimeRepository.save(registeredTime);
        id = savedMockedData.getId();
    }

    @Test
    @DisplayName("save")
    public void whenSave__thenSuccessful() {
        RegisteredTime actualResult = registeredTimeRepository.save(registeredTime);
        assertNotNull(actualResult);
    }

    @Test
    @DisplayName("findAllById_ConsultantIdOrderById_StartDateAsc")
    public void givenRegisteredTimeList__whenFindAllByStartDateAsc__then2ItemsWhere04FebIsFirstItem() {
        var mockedDataList = RegisteredTimeRepositoryMockedData.createMockedRegisteredTime04Feb();
        registeredTimeRepository.save(mockedDataList);
        var actualResult = registeredTimeRepository.findAllById_ConsultantIdOrderById_StartDateAsc(mockedId);
        assertEquals(registeredTime.getId().getStartDate(), actualResult.get(1).getId().getStartDate());
        assertEquals(mockedDataList.getId().getStartDate(), actualResult.getFirst().getId().getStartDate());
        assertEquals(2, actualResult.size());
    }

    @Test
    @DisplayName("findFirstById_ConsultantIdOrderByEndDateDesc")
    public void givenRegisteredTimeList__whenFindAllByEndDateDesc__thenReturn06Feb() {
        var mockedDataList = RegisteredTimeRepositoryMockedData.createMockedRegisteredTime06Feb();
        registeredTimeRepository.save(mockedDataList);
        var actualResult = registeredTimeRepository.findFirstById_ConsultantIdOrderByEndDateDesc(mockedId);
        assertEquals(mockedDataList.getId().getStartDate(), actualResult.getId().getStartDate());
    }

    @Test
    @DisplayName("countAllById_ConsultantIdAndTypeIs")
    public void givenNoSemesterRegistered__whenCountAllByTypeSemester__thenReturn0() {
        Integer actualResult = registeredTimeRepository.countAllById_ConsultantIdAndTypeIs(mockedId, "Semester").orElse(0);
        assertEquals(0, actualResult);
    }

    @Test
    @DisplayName("countAllById_ConsultantIdAndTypeIs")
    public void givenSemesterRegistered__whenCountAllBySemester__thenReturn1() {
        var mockedListOfRegisteredTime = RegisteredTimeRepositoryMockedData.createMockedListOfRegisteredTimeWithOneSemesterRegistered();
        registeredTimeRepository.saveAll(mockedListOfRegisteredTime);
        var actualResult = registeredTimeRepository.countAllById_ConsultantIdAndTypeIs(mockedId, "Semester").orElse(0);
        assertEquals(1, actualResult);
    }

    @Test
    @DisplayName("getSumOfTotalHoursByConsultantIdAndType")
    public void givenRegisteredTimeListAndSemester__whenGetSumOfTotalHoursByType__thenReturn16H() {
        var mockedListOfRegisteredTime = RegisteredTimeRepositoryMockedData.createMockedListOfRegisteredTime();
        registeredTimeRepository.saveAll(mockedListOfRegisteredTime);
        double actualResult = registeredTimeRepository.getSumOfTotalHoursByConsultantIdAndType(mockedId, "Semester").orElse(0D);
        assertEquals(16D, actualResult);
    }

    @Test
    @DisplayName("getSumOfTotalHoursByConsultantIdAndType")
    public void givenRegisteredTimeListAndKonsultTid__whenGetSumOfTotalHoursByType__thenReturn16H() {
        var mockedListOfRegisteredTime = RegisteredTimeRepositoryMockedData.createMockedListOfRegisteredTime();
        registeredTimeRepository.saveAll(mockedListOfRegisteredTime);
        double actualResult = registeredTimeRepository.getSumOfTotalHoursByConsultantIdAndType(mockedId, "Konsult-tid").orElse(0D);
        assertEquals(16D, actualResult);
    }

    @Test
    @DisplayName("getSumOfTotalHoursByConsultantIdAndType")
    public void givenRegisteredTimeListAndEgenAdministration__whenGetSumOfTotalHoursByType__thenReturn0H() {
        var mockedListOfRegisteredTime = RegisteredTimeRepositoryMockedData.createMockedListOfRegisteredTime();
        registeredTimeRepository.saveAll(mockedListOfRegisteredTime);
        double actualResult = registeredTimeRepository.getSumOfTotalHoursByConsultantIdAndType(mockedId, "Egen administration").orElse(0D);
        assertEquals(0D, actualResult);
    }

    @Test
    @DisplayName("findFirstById_ConsultantIdAndTypeIsOrderByEndDateDesc")
    public void givenRegisteredTimeListAndTypeSemester__whenFindFirstByTypeAndEndDateDesc__then05Apr() {
        var mockedListOfRegisteredTime = RegisteredTimeRepositoryMockedData.createMockedListOfRegisteredTime();
        registeredTimeRepository.saveAll(mockedListOfRegisteredTime);
        RegisteredTime actualResult = registeredTimeRepository.findFirstById_ConsultantIdAndTypeIsOrderByEndDateDesc(mockedId, "Semester");
        assertEquals(LocalDateTime.parse("2024-04-05 00:00:00", formatterSeconds), actualResult.getId().getStartDate());
    }

    @Test
    void shouldReturn_ListOf1_HandM() {
        List<String> expectedResultList = List.of("H&M");
        String expectedProjectName = "H&M";
        List<String> actualResult = registeredTimeRepository.findDistinctProjectNameBydId_ConsultantIdOrderById_StartDateAsc(mockedId);
        assertEquals(expectedResultList.size(), actualResult.size());
        assertEquals(expectedProjectName, actualResult.getFirst());
    }

    @Test
    void shouldReturn_ListOf2_HandM() {
        /* ARRANGE */
        String clientA = "Client A";
        String clientB = "Client B";
        UUID randomUUID = UUID.randomUUID();
        RegisteredTime registeredTime1 = new RegisteredTime(new RegisteredTimeKey(randomUUID, LocalDateTime.parse("2024-02-05 00:00:00", formatterSeconds)),
                "Konsult-tid",
                LocalDateTime.parse("2024-02-05 23:59:59", formatterSeconds),
                8D,
                clientA);
        registeredTimeRepository.save(registeredTime1);
        RegisteredTime registeredTime2 = new RegisteredTime(new RegisteredTimeKey(randomUUID, LocalDateTime.parse("2024-02-06 00:00:00", formatterSeconds)),
                "Konsult-tid",
                LocalDateTime.parse("2024-02-06 23:59:59", formatterSeconds),
                8D,
                clientB);
        registeredTimeRepository.save(registeredTime2);
        UUID consultantId = registeredTime1.getId().getConsultantId();
        List<String> expectedResult = List.of(clientA, clientB);
        List<String> actualResult = registeredTimeRepository.findDistinctProjectNameBydId_ConsultantIdOrderById_StartDateAsc(consultantId);
        assertEquals(expectedResult.size(), actualResult.size());
        assertEquals(clientA, actualResult.getFirst());
        assertEquals(clientB, actualResult.get(1));
        registeredTimeRepository.delete(registeredTime1);
        registeredTimeRepository.delete(registeredTime2);
    }

    @Test
    void shouldReturn_05_02_2024_ForStartDate() {
        LocalDate expectedResult = LocalDate.parse("2024-02-05");
        LocalDate actualResult = registeredTimeRepository.findFirstByProjectNameAndId_ConsultantIdOrderById_StartDateAsc(
                        "H&M", mockedId)
                .getId().getStartDate().toLocalDate();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void shouldReturn_06_02_2024_ForEndDate() {
        RegisteredTime registeredTime1 = new RegisteredTime(new RegisteredTimeKey(mockedId, LocalDateTime.parse("2024-02-06 00:00:00", formatterSeconds)),
                "Konsult-tid",
                LocalDateTime.parse("2024-02-06 23:59:59", formatterSeconds),
                8D,
                "H&M");
        registeredTimeRepository.save(registeredTime1);

        LocalDate expectedResult = LocalDate.parse("2024-02-06");
        /* ACT */
        LocalDate actualResult = registeredTimeRepository.findFirstByProjectNameAndId_ConsultantIdOrderByEndDateDesc(
                        "H&M", mockedId)
                .getEndDate().toLocalDate();
        /* ASSERT */
        assertEquals(expectedResult, actualResult);
    }
}