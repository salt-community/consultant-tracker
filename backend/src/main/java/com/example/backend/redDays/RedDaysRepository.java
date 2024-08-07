package com.example.backend.redDays;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RedDaysRepository extends JpaRepository<RedDays, UUID> {
    List<RedDays> findAllByCountry(String country);
}
