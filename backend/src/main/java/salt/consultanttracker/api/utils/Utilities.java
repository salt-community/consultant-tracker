package salt.consultanttracker.api.utils;

import static salt.consultanttracker.api.utils.Country.SWEDEN;

public class Utilities {
    public static boolean isWeekend(int day) {
        int SATURDAY = 6;
        int SUNDAY = 7;
        return day == SATURDAY || day == SUNDAY;
    }

    public static double countRemainingDays(Double workedHours, String country) {
        final double standardWorkingHours = getStandardWorkingHours(country);
        final int totalHours = getTotalHours(country);
        return Math.ceil((totalHours - workedHours) / standardWorkingHours );
    }

    public static double getStandardWorkingHours(String country) {
        boolean isSweden = country.equals(SWEDEN.country);
        double STANDARD_WORKING_HOURS_SE = 8;
        double STANDARD_WORKING_HOURS_NO = 7.5;
        return isSweden ? STANDARD_WORKING_HOURS_SE : STANDARD_WORKING_HOURS_NO;
    }

    public static int getTotalHours(String country) {
        boolean isSweden = country.equals(SWEDEN.country);
        int TOTAL_HOURS_SE = 2024;
        int TOTAL_HOURS_NO = 1905;
        return isSweden ? TOTAL_HOURS_SE : TOTAL_HOURS_NO;
    }

    public static int getTotalDaysByCountry(String country) {
        boolean isSweden = country.equals(SWEDEN.country);
        int TOTAL_DAYS_SE = 253;
        int TOTAL_DAYS_NO = 254;
        return isSweden ? TOTAL_DAYS_SE : TOTAL_DAYS_NO;
    }

    public static double roundToOneDecimalPoint(double number) {
        return Math.round(number * 10.0) / 10.0;
    }

    public static double roundToTwoDecimalPoints(double number) {
        return Math.round(number * 100.0) / 100.0;
    }
}
