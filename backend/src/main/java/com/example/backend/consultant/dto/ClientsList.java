package com.example.backend.consultant.dto;

import java.time.LocalDate;

public record ClientsList(String name, LocalDate startDate, LocalDate endDate) {
}
