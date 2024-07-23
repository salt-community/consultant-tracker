package com.example.backend.consultant.dto;

import com.example.backend.registeredTime.RegisteredTimeKey;

import java.time.LocalDateTime;
import java.util.UUID;

public record ConsultantTimeDto(RegisteredTimeKey itemId,
                                UUID consultantId,
                                LocalDateTime startDate,
                                LocalDateTime endDate,
                                String dayType,
                                int totalDays) {
}
