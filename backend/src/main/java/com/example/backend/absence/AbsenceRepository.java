package com.example.backend.absence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AbsenceRepository extends JpaRepository<Absence, UUID> {
}
