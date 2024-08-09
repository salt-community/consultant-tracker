package com.example.backend.consultant.dto;


public record TotalDaysStatisticsDto(
        double totalRemainingDays,
        int totalWorkedDays,
        int totalVacationDaysUsed,
        double totalRemainingHours,
        double totalWorkedHours
) {

}
