package com.example.backend.redDay;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RedDayRepository extends JpaRepository<RedDay, RedDayKey> {
    List<RedDay> findAllById_CountryCode(String country);
    RedDay findFirstByOrderById_DateDesc();
}
