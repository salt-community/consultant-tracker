package com.example.backend.consultant.dto;

import com.example.backend.consultant.Consultant;
import com.example.backend.demo.DemoConsultant;
import com.example.backend.reddays.CountryCode;
import com.example.backend.registeredtime.dto.RegisteredTimeResponseDto;
import com.example.backend.timechunks.TimeChunks;

import java.util.List;
import java.util.UUID;

import static com.example.backend.utils.Country.SWEDEN;

public record ConsultantResponseDto(UUID id,
                                    String fullName,
                                    String email,
                                    String responsiblePt,
                                    String client,
                                    String country,
                                    TotalDaysStatisticsDto totalDaysStatistics,
                                    List<RegisteredTimeResponseDto> registeredTimeDtoList) {

    public static ConsultantResponseDto toDto(
            Consultant consultant,
            TotalDaysStatisticsDto totalDaysStatistics,
            List<TimeChunks> timeChunks) {
        return new ConsultantResponseDto(
                consultant.getId(),
                consultant.getFullName(),
                consultant.getEmail(),
                consultant.getSaltUser() != null ? consultant.getSaltUser().getFullName() : "",
                consultant.getClient(),
                consultant.getCountry().equals(SWEDEN.country) ? CountryCode.SE.countryCode : CountryCode.NO.countryCode,
                totalDaysStatistics,
                timeChunks.stream().map(RegisteredTimeResponseDto::fromTimeChunks).toList()
                );
    }
    public static ConsultantResponseDto toDto(
            DemoConsultant consultant,
            TotalDaysStatisticsDto totalDaysStatistics,
            List<TimeChunks> timeChunks) {
        return new ConsultantResponseDto(
                consultant.getId(),
                consultant.getFullName(),
                consultant.getEmail(),
                consultant.getResponsiblePT(),
                consultant.getClient(),
                consultant.getCountry().equals(SWEDEN.country) ? CountryCode.SE.countryCode : CountryCode.NO.countryCode,
                totalDaysStatistics,
                timeChunks.stream().map(RegisteredTimeResponseDto::fromTimeChunks).toList()
        );
    }
}
