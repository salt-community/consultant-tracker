package com.example.backend.registeredtime.dto;

import java.time.LocalDateTime;

public record RegisteredTimeDto(LocalDateTime startDate,
                                LocalDateTime endDate,
                                String type,
                                String projectName,
                                int days) {
}
