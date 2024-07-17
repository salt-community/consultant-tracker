package com.example.backend.client.dto;

public record TimekeeperUserResponseDto(String firstName,
                                        String lastName,
                                        String email,
                                        String phone,
                                        Long id) {
}
