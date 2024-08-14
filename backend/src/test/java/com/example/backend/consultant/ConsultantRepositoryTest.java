package com.example.backend.consultant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ConsultantRepositoryTest {

    @Autowired
    ConsultantRepository repo;
    UUID id = UUID.randomUUID();
    UUID savedId = null;


    @BeforeEach
    void setUp() {
        Consultant consultant = new Consultant(id,
                "John Doe",
                "john.doe@gmail.com",
                null,
                1111L,
                "Jane Doe",
                "H&M",
                "Sverige",
                true);
        Consultant savedConsultant = repo.save(consultant);
        savedId = savedConsultant.getId();

    }

    @Test
    void shouldReturnTrueForTimekeeperId() {
        assertTrue(repo.existsByTimekeeperId(1111L));
    }

    @Test
    void shouldReturnSverigeForCountry() {
        String actualResult = repo.findCountryById(savedId);
        assertEquals("Sverige", actualResult);
    }

    @Test
    void shouldReturnListOfSize2() {
        var finaAllActive = repo.findAllByActiveTrue();
        assertEquals(2, finaAllActive.size());
    }

    @Test
    void shouldReturnListOfSize1() {
        Pageable pageRequest = PageRequest.of(0,5);
        Page<Consultant> activeJohn = repo.findAllByActiveTrueAndFilterByName("john", pageRequest);
        int expectedResult = 1;
        assertEquals(expectedResult, activeJohn.getTotalElements());
    }
}