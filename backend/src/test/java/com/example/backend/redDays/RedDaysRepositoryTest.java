package com.example.backend.redDays;

import org.junit.jupiter.api.BeforeEach;
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
public class RedDaysRepositoryTest {
    @Autowired
    RedDaysRepository redDaysRepository;
    RedDays redDay;
    UUID mockedId = UUID.fromString("01438d07-91cb-4f4d-adab-aa050462779e");
    UUID id;

    @BeforeEach
    public void setUp() {
        redDay = new RedDays(mockedId,
                LocalDate.parse("2024-01-01"),
                "New Year",
                "SE");
        RedDays savedMockedData = redDaysRepository.save(redDay);
        id = savedMockedData.getId();
    }

    @Test
    public void shouldSuccessWhenSave() {
        redDay = new RedDays(mockedId,
                LocalDate.parse("2024-01-01"),
                "New Year",
                "SE");
        RedDays savedMockedData = redDaysRepository.save(redDay);
        id = savedMockedData.getId();
        RedDays actualResult = redDaysRepository.findById(id).orElse(null);
        assertNotNull(actualResult);
    }

    @Test
    public void shouldReturnOneWhenFindAll() {
        List<RedDays> actualResult = redDaysRepository.findAll();
        assertEquals(1, actualResult.size());
    }

    @Test
    public void shouldFindOneWhenFindAllByCountry() {
        List<RedDays> actualResult = redDaysRepository.findAllByCountry("SE");
        assertEquals(1, actualResult.size());
    }

    @Test
    public void shouldReturnEmptyWhenFindAllByCountry() {
        List<RedDays> actualResult = redDaysRepository.findAllByCountry("NO");
        assertTrue(actualResult.isEmpty());
    }
}
