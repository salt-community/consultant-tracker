package com.example.backend.redDay;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RedDayRepository extends JpaRepository<RedDay, UUID> {
    List<RedDay> findAllByCountry(String country);
}
