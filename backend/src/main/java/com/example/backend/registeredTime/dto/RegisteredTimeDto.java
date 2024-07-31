package com.example.backend.registeredTime.dto;

import java.time.LocalDateTime;

public record RegisteredTimeDto(LocalDateTime startDate,
                                LocalDateTime endDate,
                                String type,
                                String projectName) {
}
