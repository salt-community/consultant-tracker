package com.example.backend.consultant.dto;

import com.example.backend.consultant.Consultant;
import com.example.backend.redDay.CountryCode;
import com.example.backend.registeredTime.dto.RegisteredTimeResponseDto;
import com.example.backend.timeChunks.TimeChunks;

import java.util.List;
import java.util.UUID;

import static com.example.backend.utils.Country.SWEDEN;

public record ConsultantResponseDto(UUID id,
                                    String fullName,
                                    String email,
                                    String phoneNumber,
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
                consultant.getPhoneNumber(),
                consultant.getResponsiblePT(),
                consultant.getClient(),
                consultant.getCountry().equals(SWEDEN.country) ? CountryCode.SE.countryCode : CountryCode.NO.countryCode,
                totalDaysStatistics,
                timeChunks.stream().map(RegisteredTimeResponseDto::fromTimeChunks).toList()
                );
    }
}
