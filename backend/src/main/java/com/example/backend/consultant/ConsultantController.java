package com.example.backend.consultant;

import com.example.backend.consultant.dto.ConsultantResponseListDto;
import com.example.backend.consultant.dto.ConsultantTimeResponseDto;
import com.example.backend.redDays.RedDaysService;
import com.example.backend.registeredTime.RegisteredTime;
import com.example.backend.registeredTime.RegisteredTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/consultants")
@RequiredArgsConstructor
@CrossOrigin
public class ConsultantController {

    private final ConsultantService consultantService;
    private final RegisteredTimeService registeredTimeService;
    private final RedDaysService redDaysService;

    @GetMapping
    public ResponseEntity<ConsultantResponseListDto> getConsultantsAndRegisteredTime(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "", required = false) String name,
            @RequestParam(defaultValue = "", required = false) String client,
            @RequestParam(defaultValue = "", required = false) String pt) {
        ConsultantResponseListDto consultantsResponse = consultantService.getAllConsultantDtos(page, pageSize, name, pt, client);
        return ResponseEntity.ok(consultantsResponse);
    }

    @GetMapping("/redDays")
    public void getConsultantById() {
        redDaysService.getRedDaysFromNager();
    }


    @GetMapping("/time")
    public ResponseEntity<ConsultantTimeResponseDto> getConsultantsHours(
            @RequestParam(name = "client", required = false) String clientId) {
        if (clientId == null || clientId.isEmpty()) {
            ConsultantTimeResponseDto result = new ConsultantTimeResponseDto(registeredTimeService.getAllConsultantsTimeItems());
            return ResponseEntity.ok(result);
        }
        return null;
    }
    @GetMapping("/registeredTimeByConsultantId")
    public ResponseEntity<List<RegisteredTime>> getConsultantsRegisteredTimeById(
            @RequestParam UUID id) {
        return ResponseEntity.ok(registeredTimeService.getRegisteredTime(id));
    }

}
