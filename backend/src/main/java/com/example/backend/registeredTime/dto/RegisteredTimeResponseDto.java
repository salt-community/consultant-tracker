package com.example.backend.registeredTime.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record RegisteredTimeResponseDto(UUID registeredTimeId,
                                        LocalDateTime startDate,
                                        LocalDateTime endDate,
                                        String type
//                                String description
) {
}
