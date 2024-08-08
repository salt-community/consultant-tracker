package com.example.backend.consultant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TotalDaysStatistics {
    private double totalRemainingDays;
    private int totalWorkedDays;
    private int totalVacationDaysUsed;
    private double totalRemainingHours;
    private double totalWorkedHours;
}
