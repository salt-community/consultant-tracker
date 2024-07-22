package com.example.backend.consultant;

import com.example.backend.consultant.dto.ConsultantResponseDto;
import com.example.backend.consultant.dto.ConsultantTimeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/consultants")
@RequiredArgsConstructor
public class ConsultantController {

    private final ConsultantService consultantService;

    @GetMapping
    public ResponseEntity<List<ConsultantResponseDto>> getConsultants() {
        List<ConsultantResponseDto> consultantsResponse = consultantService.getAllConsultantDtos();
        return ResponseEntity.ok(consultantsResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultantResponseDto> getConsultantById(@PathVariable UUID id) {
        ConsultantResponseDto consultant = consultantService.findConsultantById(id);
        return ResponseEntity.ok(consultant);
    }

    @GetMapping("/time/{id}")
    public ResponseEntity<Double> getConsultancyHoursByUserId(@PathVariable UUID id) {
        Double getHours = consultantService.getConsultancyHoursByUserId(id);
        return ResponseEntity.ok(getHours);
    }

    @GetMapping("/time")
    public ResponseEntity<ConsultantTimeResponseDto> getConsultantsHours(
            @RequestParam(name = "client", required = false) String clientId) {
        if (clientId == null || clientId.isEmpty()) {
            ConsultantTimeResponseDto result = new ConsultantTimeResponseDto(consultantService.getAllConsultantsTimeItems());
            return ResponseEntity.ok(result);
        }
        return null;
    }
}
