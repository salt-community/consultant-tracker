package salt.consultanttracker.api.consultant.dto;


public record TotalDaysStatisticsDto(
        double totalRemainingDays,
        int totalWorkedDays,
        int totalVacationDaysUsed,
        int totalSickDays,
        int totalParentalLeaveDays,
        int totalVABDays,
        int totalUnpaidVacationDays,
        double totalRemainingHours,
        double totalWorkedHours
) {

}
