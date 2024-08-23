package com.example.backend.demo.demoConsultant;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DemoConsultantRepository extends JpaRepository<DemoConsultant, UUID> {
    @Query("SELECT t FROM DemoConsultant t WHERE t.active = true AND t.fullName iLIKE %:fullName% AND t.responsiblePT IN :ptList AND t.client IN :clientsList ORDER BY t.fullName ASC")
    Page<DemoConsultant> findAllByActiveTrueAndFilterByNameAndResponsiblePtAndClientsOrderByFullNameAsc(String fullName, Pageable pageable, List<String> ptList, List<String> clientsList);

    List<DemoConsultant> findAllByActiveTrue();
}
