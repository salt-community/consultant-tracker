package com.example.backend.consultant.dto;

import java.util.List;

public record ConsultantResponseListDto(int pageNumber,
                                        int totalPages,
                                        long totalConsultants,
                                        List<ConsultantResponseDto> consultants) {
}
