package com.example.backend.consultant.dto;

import java.util.UUID;

public record ConsultantResponseDto(UUID id, String fullName, String email, String phoneNumber) {
}
