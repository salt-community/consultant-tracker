package com.example.backend.consultant.dto;

import com.example.backend.registeredTime.RegisteredTimeKey;

import java.time.LocalDateTime;

public record ConsultantTimeDto(RegisteredTimeKey itemId,
                                LocalDateTime endDate,
                                String dayType,
                                double totalHours,
                                String projectName) {
}
