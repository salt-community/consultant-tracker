package com.example.backend.client.timekeeper.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record TimekeeperUserListResponseDto(
        @JsonProperty("results")
        List<TimekeeperUserDto> timekeeperUsers,
        int totalPages
) {
}
