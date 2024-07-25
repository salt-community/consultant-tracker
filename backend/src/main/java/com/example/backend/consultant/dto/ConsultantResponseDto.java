package com.example.backend.consultant.dto;

import com.example.backend.consultant.Consultant;
import com.example.backend.registeredTime.dto.RegisteredTimeDto;

import java.util.List;
import java.util.UUID;

public record ConsultantResponseDto(UUID id,
                                    String fullName,
                                    String email,
                                    String phoneNumber,
                                    List<RegisteredTimeDto> registeredTimeDtoList) {

    public static ConsultantResponseDto toDto(Consultant consultant, List<RegisteredTimeDto> registeredTimeDtoList) {
        return new ConsultantResponseDto(
                consultant.getId(),
                consultant.getFullName(),
                consultant.getEmail(),
                consultant.getPhoneNumber(),
                registeredTimeDtoList
                );
    }

}
