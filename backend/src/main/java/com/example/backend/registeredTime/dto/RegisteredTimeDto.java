package com.example.backend.registeredTime.dto;

import com.example.backend.registeredTime.RegisteredTimeKey;

import java.time.LocalDateTime;
import java.util.UUID;

public record RegisteredTimeDto(UUID registeredTimeId,
                                LocalDateTime startDate,
                                LocalDateTime endDate,
                                String type
//                                String description
) {
}
