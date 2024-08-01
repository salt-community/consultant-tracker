package com.example.backend.consultant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TotalDaysStatistics {
    private int totalRemainingDays;
    private int totalWorkedDays;
    private int totalVacationDaysUsed;
}
