package com.example.backend.consultant.dto;

import com.example.backend.consultant.Consultant;
import com.example.backend.consultant.TotalDaysStatistics;
import com.example.backend.registeredTime.dto.RegisteredTimeResponseDto;

import java.util.List;
import java.util.UUID;

public record ConsultantResponseDto(UUID id,
                                    String fullName,
                                    String email,
                                    String phoneNumber,
                                    String responsiblePt,
                                    String client,
                                    TotalDaysStatistics totalDaysStatistics,
                                    List<RegisteredTimeResponseDto> registeredTimeDtoList) {

    public static ConsultantResponseDto toDto(Consultant consultant, TotalDaysStatistics totalDaysStatistics, List<RegisteredTimeResponseDto> registeredTimeDtoList) {
        return new ConsultantResponseDto(
                consultant.getId(),
                consultant.getFullName(),
                consultant.getEmail(),
                consultant.getPhoneNumber(),
                consultant.getResponsiblePT(),
                consultant.getClient(),
                totalDaysStatistics,
                registeredTimeDtoList
                );
    }

}
