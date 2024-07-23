package com.example.backend.client.dto;

import java.time.LocalDateTime;

public record TimekeeperRegisteredTimeResponseDto(
        Float totalHours,
        String activityName,
        LocalDateTime startTime) {
}
