package com.example.backend.consultant.dto;

import com.example.backend.consultant.Consultant;
import com.example.backend.registeredTime.dto.RegisteredTimeResponseDto;

import java.util.List;
import java.util.UUID;

public record ConsultantResponseDto(UUID id,
                                    String fullName,
                                    String email,
                                    String phoneNumber,
                                    List<RegisteredTimeResponseDto> registeredTimeDtoList) {

    public static ConsultantResponseDto toDto(Consultant consultant, List<RegisteredTimeResponseDto> registeredTimeDtoList) {
        return new ConsultantResponseDto(
                consultant.getId(),
                consultant.getFullName(),
                consultant.getEmail(),
                consultant.getPhoneNumber(),
                registeredTimeDtoList
                );
    }

}
