package com.example.backend.registeredTime;

import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RegisteredTimeRepository extends JpaRepository<RegisteredTime, RegisteredTimeKey> {
     List<RegisteredTime> findAllById_ConsultantIdOrderById_StartDateAsc(UUID id);
     RegisteredTime findFirstById_ConsultantIdOrderByEndDateDesc(UUID id);
     Optional<Integer> countAllById_ConsultantIdAndTypeIs(UUID id, String type);
     @Query("SELECT SUM(t.totalHours) FROM RegisteredTime t WHERE t.id.consultantId = (:id) AND t.type LIKE %:type%")
     Optional<Double> getSumOfTotalHoursByConsultantIdAndType(UUID id, String type);
     RegisteredTime findFirstById_ConsultantIdAndTypeIsOrderByEndDateDesc(UUID id, String type);

     @Query("SELECT DISTINCT t.projectName FROM RegisteredTime t WHERE t.id.consultantId = (:consultantId)")
     List<String> findDistinctProjectNameBydId_ConsultantId(@Param("consultantId")UUID consultantId);

     RegisteredTime findFirstByProjectNameAndId_ConsultantIdOrderById_StartDateAsc(String projectName, UUID consultantId);
     RegisteredTime findFirstByProjectNameAndId_ConsultantIdOrderByEndDateDesc(String projectName, UUID consultantId);
}
