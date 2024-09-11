package com.example.backend.consultant;

import com.example.backend.saltUser.SaltUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
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
                1111L,
                UUID.randomUUID().toString(),
                true,
                "H&M",
                "Sverige",
                new SaltUser(UUID.fromString("9b5dcb09-bd42-4306-a964-da0727c44a68"),
                        "Jane Doe",
                        "jane.doe@appliedtechnology.se" ,
                        "PT")
        );
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
        Pageable pageRequest = PageRequest.of(0, 5);
        Page<Consultant> activeJohn = repo.findAllByActiveTrueAndFilterByNameAndResponsiblePtAndClientsOrderByFullNameAsc(
                "john",
                pageRequest,
                List.of("Jane Doe"),
                List.of("H&M"));
        int expectedResult = 1;
        assertEquals(expectedResult, activeJohn.getTotalElements());
    }
}