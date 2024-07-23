package com.example.backend.registeredTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RegisteredTimeRepository extends JpaRepository<RegisteredTime, RegisteredTimeKey> {
     List<RegisteredTime> findAllById_ConsultantId(UUID id);
}
