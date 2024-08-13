package com.example.backend.registeredTime;

import org.junit.jupiter.api.BeforeEach;
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
    public void shouldSuccessfullySaveRegisteredTime() {
        RegisteredTime actualResult = registeredTimeRepository.save(registeredTime);
        assertNotNull(actualResult);
    }

    @Test
    public void shouldReturn2TimeItemsInAscOrder() {
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
    public void shouldReturnFirstWhenDescOrder() {
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
    public void shouldReturn0WhenNoVacationTaken(){
        Integer actualResult = registeredTimeRepository.countAllById_ConsultantIdAndTypeIs(mockedId, "Semester").orElse(0);
        assertEquals(0,actualResult);
    }
    @Test
    public void shouldReturn1WhenVacationTaken(){
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
    public void shouldReturn16HoursWhenTypeSemester(){
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
    public void shouldReturn16HoursWhenTypeKonsultTid(){
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
    public void shouldReturn16HoursWhenTypeEgenAdministration(){
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
    public void shouldReturn04AprWhenTypeSemesterAndDesc(){
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