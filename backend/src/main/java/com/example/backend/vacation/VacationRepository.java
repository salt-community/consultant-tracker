package com.example.backend.vacation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VacationRepository extends JpaRepository<Vacation, UUID> {
}
