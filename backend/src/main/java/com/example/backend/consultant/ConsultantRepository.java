package com.example.backend.consultant;

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
    @Query("SELECT t.country FROM Consultant t WHERE t.id = (:id)")
//    @Query("SELECT t.country FROM Consultant t WHERE (:id) IS NOT NULL AND IN (:id)")
    String findCountryById(UUID id);
}
