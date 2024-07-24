package com.example.backend.consultant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ConsultantRepository extends JpaRepository<Consultant, UUID> {
    boolean existsByTimekeeperId(Long id);
    Consultant save(Consultant consultant);

    List<Consultant> findAllByActiveTrue();
}
