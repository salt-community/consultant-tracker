package com.example.backend.redDay;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DataJpaTest
public class RedDayRepositoryTest {
    @Autowired
    RedDayRepository redDaysRepository;
    RedDay redDay;
    UUID mockedId = UUID.fromString("01438d07-91cb-4f4d-adab-aa050462779e");
    UUID id;

    @BeforeEach
    public void setUp() {
        redDay = new RedDay(mockedId,
                LocalDate.parse("2024-01-01"),
                "New Year",
                "SE");
        RedDay savedMockedData = redDaysRepository.save(redDay);
        id = savedMockedData.getId();
    }

    @Test
    @DisplayName("save")
    public void givenRedDay__whenSave__thenSuccessful() {
        redDay = new RedDay(mockedId,
                LocalDate.parse("2024-01-01"),
                "New Year",
                "SE");
        RedDay savedMockedData = redDaysRepository.save(redDay);
        id = savedMockedData.getId();
        RedDay actualResult = redDaysRepository.findById(id).orElse(null);
        assertNotNull(actualResult);
    }

    @Test
    @DisplayName("findAll")
    public void  whenFindAll__thenListSize2() {
        var mockedRedDays =  new RedDay(UUID.randomUUID(),
                LocalDate.parse("2024-01-01"),
                "New Year",
                "SE");
        redDaysRepository.save(mockedRedDays);
        List<RedDay> actualResult = redDaysRepository.findAll();
        assertEquals(2, actualResult.size());
    }

    @Test
    @DisplayName("findAllByCountry")
    public void givenSE__whenFindAllByCountry__thenListSize1() {
        List<RedDay> actualResult = redDaysRepository.findAllByCountry("SE");
        assertEquals(1, actualResult.size());
    }

    @Test
    @DisplayName("findAllByCountry")
    public void givenNO__whenFindAllByCountry__thenEmptyList() {
        List<RedDay> actualResult = redDaysRepository.findAllByCountry("NO");
        assertTrue(actualResult.isEmpty());
    }
}
