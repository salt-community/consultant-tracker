package com.example.backend.consultant;

import com.example.backend.client.dagsmart.DagsmartClient;
import com.example.backend.consultant.dto.ConsultantResponseDto;
import com.example.backend.consultant.dto.ConsultantResponseListDto;
import com.example.backend.consultant.dto.ConsultantTimeResponseDto;
import com.example.backend.redDays.RedDaysService;
import com.example.backend.registeredTime.RegisteredTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/consultants")
@RequiredArgsConstructor
public class ConsultantController {

    private final ConsultantService consultantService;
    private final RegisteredTimeService registeredTimeService;
    private final RedDaysService redDaysService;
    @GetMapping
    public ResponseEntity<ConsultantResponseListDto> getConsultantsAndRegisteredTime(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        redDaysService.getRedDaysFromDagsmart();
        ConsultantResponseListDto consultantsResponse = consultantService.getAllConsultantDtos(page, pageSize);
        return ResponseEntity.ok(consultantsResponse);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<ConsultantResponseDto> getConsultantById(@PathVariable UUID id) {
//        ConsultantResponseDto consultant = consultantService.getConsultantsRegisteredTimeItems(id);
//        return ResponseEntity.ok(consultant);
//    }

    @GetMapping("/time")
    public ResponseEntity<ConsultantTimeResponseDto> getConsultantsHours(
            @RequestParam(name = "client", required = false) String clientId) {
        if (clientId == null || clientId.isEmpty()) {
            ConsultantTimeResponseDto result = new ConsultantTimeResponseDto(registeredTimeService.getAllConsultantsTimeItems());
            return ResponseEntity.ok(result);
        }
        return null;
    }
}
