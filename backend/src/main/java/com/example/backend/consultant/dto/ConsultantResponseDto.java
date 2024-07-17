package com.example.backend.consultant.dto;

import com.example.backend.consultant.Consultant;

import java.util.UUID;

public record ConsultantResponseDto(UUID id,
                                    String fullName,
                                    String email,
                                    String phoneNumber) {

    public static ConsultantResponseDto toDto(Consultant consultant) {
        return new ConsultantResponseDto(
                consultant.getId(),
                consultant.getFullName(),
                consultant.getEmail(),
                consultant.getPhoneNumber()
        );
    }

}
