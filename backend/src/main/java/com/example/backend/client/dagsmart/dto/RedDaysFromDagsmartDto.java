package com.example.backend.client.dagsmart.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RedDaysFromDagsmartDto(String date,
                                     String code,
                                     @JsonProperty("name")
                                     Name name) {
}
