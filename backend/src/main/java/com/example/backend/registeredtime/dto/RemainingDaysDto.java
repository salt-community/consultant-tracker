package com.example.backend.registeredtime.dto;

import java.time.LocalDateTime;

public record RemainingDaysDto(LocalDateTime endDate, int remainingDays) {
}
