package com.example.backend.demo.dto;


import java.util.List;

public record DemoConsultantResponseListDto(int pageNumber,
                                            int totalPages,
                                            long totalConsultants,
                                            List<DemoConsultantResponseDto> consultants) {
}
