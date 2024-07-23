package com.example.backend.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record TimekeeperRegisteredTimeListResponseDto(@JsonProperty("results")
                                                   List<TimekeeperRegisteredTimeResponseDto> consultancyTime) {
}
