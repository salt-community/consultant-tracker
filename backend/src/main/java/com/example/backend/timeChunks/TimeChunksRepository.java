package com.example.backend.timeChunks;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TimeChunksRepository extends JpaRepository<TimeChunks, TimeChunksKey> {

    List<TimeChunks> findAllById_ConsultantId(UUID consultantId);
}
