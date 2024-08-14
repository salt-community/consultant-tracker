package com.example.backend.consultant.dto;

import java.util.Set;

public record ClientsAndPtsListDto(Set<String> clients, Set<String> pts) {
}
