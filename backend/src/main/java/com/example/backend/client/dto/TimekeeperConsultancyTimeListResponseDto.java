package com.example.backend.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record TimekeeperConsultancyTimeListResponseDto(@JsonProperty("results")
                                                   List<TimekeeperConsultancyTimeResponseDto> consultancyTime) {
}
