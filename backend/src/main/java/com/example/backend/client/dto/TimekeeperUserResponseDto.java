package com.example.backend.client.dto;

import com.example.backend.tag.Tag;

import java.util.List;

public record TimekeeperUserResponseDto(String firstName,
                                        String lastName,
                                        String email,
                                        String phone,
                                        List<Tag> tags,
                                        Long id) {
}
