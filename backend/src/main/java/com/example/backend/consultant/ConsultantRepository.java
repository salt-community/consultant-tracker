package com.example.backend.consultant;

import com.example.backend.registeredTime.RegisteredTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ConsultantRepository extends JpaRepository<Consultant, UUID> {
    boolean existsByTimekeeperId(Long id);
    List<Consultant> findAllByActiveTrue();
    @Query("SELECT t FROM Consultant t WHERE t.active = true AND t.fullName iLIKE %:fullName%")
    Page<Consultant> findAllByActiveTrueAndFilterByName(String fullName, Pageable pageable);
}
