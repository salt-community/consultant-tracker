package com.example.backend.consultant;

import com.example.backend.consultant.dto.ConsultantResponseDto;
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
        List<ConsultantResponseDto> consultantsResponse =  consultantService.getAllConsultants();
        return ResponseEntity.ok(consultantsResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultantResponseDto> getConsultantById(@PathVariable UUID id) {
        ConsultantResponseDto consultant = consultantService.findConsultantById(id);
        return ResponseEntity.ok(consultant);
    }

    @GetMapping("/time/{id}")
    public ResponseEntity<Float> getConsultancyHoursByUserId(@PathVariable UUID id){
        Float getHours = consultantService.getConsultancyHoursByUserId(id);
        return ResponseEntity.ok(getHours);
    }
}
