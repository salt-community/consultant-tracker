package com.example.backend.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record TimekeeperUserListResponseDto(
        @JsonProperty("results")
        List<TimekeeperUserResponseDto> timekeeperUsers
) {
}
