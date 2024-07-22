package com.example.backend.client.dto;

import java.time.LocalDateTime;

public record TimekeeperConsultancyTimeResponseDto(
        Float totalHours,
        String activityName,
        LocalDateTime startTime) {
}
