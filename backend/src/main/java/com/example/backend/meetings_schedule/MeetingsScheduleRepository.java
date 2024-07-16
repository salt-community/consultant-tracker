package com.example.backend.meetings_schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MeetingsScheduleRepository extends JpaRepository<MeetingsSchedule, UUID> {
}
