package com.example.backend.redDays.dto;

import java.time.LocalDate;
import java.util.List;

public record RedDaysResponseDto(List<LocalDate> redDaysSE, List<LocalDate> redDaysNO) {
}
