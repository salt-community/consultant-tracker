package com.example.backend.client.dto;

import lombok.Data;

public record TimekeeperUserResponseDto(String firstName,
                                        String lastName,
                                        String email,
                                        String phone,
                                        Long id) {
}
