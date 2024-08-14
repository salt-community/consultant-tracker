package com.example.backend.registeredTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        var mockedDataList = new RegisteredTime(new RegisteredTimeKey(mockedId, LocalDateTime.parse("2024-02-04 00:00:00", formatterSeconds)),
                "Konsult-tid",
                LocalDateTime.parse("2024-02-04 23:59:59", formatterSeconds),
                8D,
                "H&M");
        registeredTimeRepository.save(mockedDataList);
        var actualResult = registeredTimeRepository.findAllById_ConsultantIdOrderById_StartDateAsc(mockedId);
        assertEquals(registeredTime.getId().getStartDate(), actualResult.get(1).getId().getStartDate());
        assertEquals(mockedDataList.getId().getStartDate(), actualResult.getFirst().getId().getStartDate());
        assertEquals(2, actualResult.size());
    }

    @Test
    @DisplayName("findFirstById_ConsultantIdOrderByEndDateDesc")
    public void givenRegisteredTimeList__whenFindAllByEndDateDesc__thenReturn06Feb() {
        var mockedDataList = new RegisteredTime(new RegisteredTimeKey(mockedId,
                LocalDateTime.parse("2024-02-06 00:00:00",
                        formatterSeconds)),
                "Konsult-tid",
                LocalDateTime.parse("2024-02-06 23:59:59", formatterSeconds),
                8D,
                "H&M");
        registeredTimeRepository.save(mockedDataList);
        var actualResult = registeredTimeRepository.findFirstById_ConsultantIdOrderByEndDateDesc(mockedId);
        assertEquals(mockedDataList.getId().getStartDate(), actualResult.getId().getStartDate());
    }

    @Test
    @DisplayName("countAllById_ConsultantIdAndTypeIs")
    public void givenNoSemesterRegistered__whenCountAllByTypeSemester__thenReturn0(){
        Integer actualResult = registeredTimeRepository.countAllById_ConsultantIdAndTypeIs(mockedId, "Semester").orElse(0);
        assertEquals(0,actualResult);
    }
    @Test
    @DisplayName("countAllById_ConsultantIdAndTypeIs")
    public void givenSemesterRegistered__whenCountAllBySemester__thenReturn1(){
        var mockedRegisteredTime2 =  new RegisteredTime(new RegisteredTimeKey(mockedId, LocalDateTime.parse("2024-03-07 00:00:00", formatterSeconds)),
                "Semester",
                LocalDateTime.parse("2024-03-07 23:59:59", formatterSeconds),
                8D,
                "H&M");
        var mockedRegisteredTime3 =  new RegisteredTime(new RegisteredTimeKey(mockedId, LocalDateTime.parse("2024-03-08 00:00:00", formatterSeconds)),
                "Konsult-tid",
                LocalDateTime.parse("2024-03-08 23:59:59", formatterSeconds),
                8D,
                "H&M");
        registeredTimeRepository.save(mockedRegisteredTime2);
        registeredTimeRepository.save(mockedRegisteredTime3);
        var actualResult = registeredTimeRepository.countAllById_ConsultantIdAndTypeIs(mockedId, "Semester").orElse(0);
        assertEquals(1, actualResult);
    }

    @Test
    @DisplayName("getSumOfTotalHoursByConsultantIdAndType")
    public void givenRegisteredTimeListAndSemester__whenGetSumOfTotalHoursByType__thenReturn16H(){
        var mockedRegisteredTime2 =  new RegisteredTime(new RegisteredTimeKey(mockedId, LocalDateTime.parse("2024-03-07 00:00:00", formatterSeconds)),
                "Semester",
                LocalDateTime.parse("2024-03-07 23:59:59", formatterSeconds),
                8D,
                "H&M");
        var mockedRegisteredTime3 =  new RegisteredTime(new RegisteredTimeKey(mockedId, LocalDateTime.parse("2024-03-08 00:00:00", formatterSeconds)),
                "Konsult-tid",
                LocalDateTime.parse("2024-03-08 23:59:59", formatterSeconds),
                8D,
                "H&M");
        var mockedRegisteredTime4 =  new RegisteredTime(new RegisteredTimeKey(mockedId, LocalDateTime.parse("2024-04-05 00:00:00", formatterSeconds)),
                "Semester",
                LocalDateTime.parse("2024-04-05 23:59:59", formatterSeconds),
                8D,
                "H&M");
        registeredTimeRepository.save(mockedRegisteredTime2);
        registeredTimeRepository.save(mockedRegisteredTime3);
        registeredTimeRepository.save(mockedRegisteredTime4);
        double actualResult = registeredTimeRepository.getSumOfTotalHoursByConsultantIdAndType(mockedId, "Semester").orElse(0D);
        assertEquals(16D, actualResult);
    }

    @Test
    @DisplayName("getSumOfTotalHoursByConsultantIdAndType")
    public void givenRegisteredTimeListAndKonsultTid__whenGetSumOfTotalHoursByType__thenReturn16H(){
        var mockedRegisteredTime2 =  new RegisteredTime(new RegisteredTimeKey(mockedId, LocalDateTime.parse("2024-03-07 00:00:00", formatterSeconds)),
                "Semester",
                LocalDateTime.parse("2024-03-07 23:59:59", formatterSeconds),
                8D,
                "H&M");
        var mockedRegisteredTime3 =  new RegisteredTime(new RegisteredTimeKey(mockedId, LocalDateTime.parse("2024-03-08 00:00:00", formatterSeconds)),
                "Konsult-tid",
                LocalDateTime.parse("2024-03-08 23:59:59", formatterSeconds),
                8D,
                "H&M");
        var mockedRegisteredTime4 =  new RegisteredTime(new RegisteredTimeKey(mockedId, LocalDateTime.parse("2024-04-05 00:00:00", formatterSeconds)),
                "Semester",
                LocalDateTime.parse("2024-04-05 23:59:59", formatterSeconds),
                8D,
                "H&M");
        registeredTimeRepository.save(mockedRegisteredTime2);
        registeredTimeRepository.save(mockedRegisteredTime3);
        registeredTimeRepository.save(mockedRegisteredTime4);
        double actualResult = registeredTimeRepository.getSumOfTotalHoursByConsultantIdAndType(mockedId, "Konsult-tid").orElse(0D);
        assertEquals(16D, actualResult);
    }
    @Test
    @DisplayName("getSumOfTotalHoursByConsultantIdAndType")
    public void givenRegisteredTimeListAndEgenAdministration__whenGetSumOfTotalHoursByType__thenReturn0H(){
        var mockedRegisteredTime2 =  new RegisteredTime(new RegisteredTimeKey(mockedId, LocalDateTime.parse("2024-03-07 00:00:00", formatterSeconds)),
                "Semester",
                LocalDateTime.parse("2024-03-07 23:59:59", formatterSeconds),
                8D,
                "H&M");
        var mockedRegisteredTime3 =  new RegisteredTime(new RegisteredTimeKey(mockedId, LocalDateTime.parse("2024-03-08 00:00:00", formatterSeconds)),
                "Konsult-tid",
                LocalDateTime.parse("2024-03-08 23:59:59", formatterSeconds),
                8D,
                "H&M");
        var mockedRegisteredTime4 =  new RegisteredTime(new RegisteredTimeKey(mockedId, LocalDateTime.parse("2024-04-05 00:00:00", formatterSeconds)),
                "Semester",
                LocalDateTime.parse("2024-04-05 23:59:59", formatterSeconds),
                8D,
                "H&M");
        registeredTimeRepository.save(mockedRegisteredTime2);
        registeredTimeRepository.save(mockedRegisteredTime3);
        registeredTimeRepository.save(mockedRegisteredTime4);
        double actualResult = registeredTimeRepository.getSumOfTotalHoursByConsultantIdAndType(mockedId, "Egen administration").orElse(0D);
        assertEquals(0D, actualResult);
    }

    @Test
    @DisplayName("findFirstById_ConsultantIdAndTypeIsOrderByEndDateDesc")
    public void givenRegisteredTimeListAndTypeSemester__whenFindFirstByTypeAndEndDateDesc__then05Apr(){
        var mockedRegisteredTime2 =  new RegisteredTime(new RegisteredTimeKey(mockedId, LocalDateTime.parse("2024-03-07 00:00:00", formatterSeconds)),
                "Semester",
                LocalDateTime.parse("2024-03-07 23:59:59", formatterSeconds),
                8D,
                "H&M");
        var mockedRegisteredTime3 =  new RegisteredTime(new RegisteredTimeKey(mockedId, LocalDateTime.parse("2024-03-08 00:00:00", formatterSeconds)),
                "Konsult-tid",
                LocalDateTime.parse("2024-03-08 23:59:59", formatterSeconds),
                8D,
                "H&M");
        var mockedRegisteredTime4 =  new RegisteredTime(new RegisteredTimeKey(mockedId, LocalDateTime.parse("2024-04-05 00:00:00", formatterSeconds)),
                "Semester",
                LocalDateTime.parse("2024-04-05 23:59:59", formatterSeconds),
                8D,
                "H&M");
        registeredTimeRepository.save(mockedRegisteredTime2);
        registeredTimeRepository.save(mockedRegisteredTime3);
        registeredTimeRepository.save(mockedRegisteredTime4);
        RegisteredTime actualResult = registeredTimeRepository.findFirstById_ConsultantIdAndTypeIsOrderByEndDateDesc(mockedId, "Semester");
        assertEquals(mockedRegisteredTime4.getId().getStartDate(), actualResult.getId().getStartDate());
    }

}