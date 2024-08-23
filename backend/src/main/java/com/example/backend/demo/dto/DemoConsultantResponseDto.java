package com.example.backend.demo.dto;

import com.example.backend.consultant.dto.TotalDaysStatisticsDto;
import com.example.backend.demo.demoConsultant.DemoConsultant;
import com.example.backend.redDay.CountryCode;
import com.example.backend.registeredTime.dto.RegisteredTimeResponseDto;

import java.util.List;
import java.util.UUID;

import static com.example.backend.utils.Country.SWEDEN;

public record DemoConsultantResponseDto(UUID id,
                                        String fullName,
                                        String email,
                                        String phoneNumber,
                                        String responsiblePt,
                                        String client,
                                        String country,
                                        TotalDaysStatisticsDto totalDaysStatistics,
                                        List<RegisteredTimeResponseDto> registeredTimeDtoList) {

    public static DemoConsultantResponseDto toDto(DemoConsultant consultant, TotalDaysStatisticsDto totalDaysStatistics, List<RegisteredTimeResponseDto> registeredTimeDtoList) {
        return new DemoConsultantResponseDto(
                consultant.getId(),
                consultant.getFullName(),
                consultant.getEmail(),
                consultant.getPhoneNumber(),
                consultant.getResponsiblePT(),
                consultant.getClient(),
                consultant.getCountry().equals(SWEDEN.country) ? CountryCode.SE.countryCode : CountryCode.NO.countryCode,
                totalDaysStatistics,
                registeredTimeDtoList
        );
    }
}
