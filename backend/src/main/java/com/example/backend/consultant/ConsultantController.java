package com.example.backend.consultant;

import com.example.backend.consultant.dto.ClientsAndPtsListDto;
import com.example.backend.consultant.dto.ConsultantResponseListDto;
import com.example.backend.consultant.dto.ConsultantTimeResponseDto;
import com.example.backend.redDays.RedDay;
import com.example.backend.redDays.RedDayService;
import com.example.backend.registeredTime.RegisteredTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/consultants")
@RequiredArgsConstructor
@CrossOrigin
public class ConsultantController {

    private final ConsultantService consultantService;
    private final RegisteredTimeService registeredTimeService;
    private final RedDayService redDaysService;
    @GetMapping
    public ResponseEntity<ConsultantResponseListDto> getConsultantsAndRegisteredTime(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "", required = false) String name,
            @RequestParam(defaultValue = "", required = false) List<String> client,
            @RequestParam(defaultValue = "", required = false) List<String> pt) {
        ConsultantResponseListDto consultantsResponse = consultantService.getAllConsultantDtos(page, pageSize, name, pt, client);
        return ResponseEntity.ok(consultantsResponse);
    }

    @GetMapping("/redDays")
    public ResponseEntity<List<RedDay>> getConsultantById() {
        return ResponseEntity.ok(redDaysService.getRedDaysFromNager(2018,2030));
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
    @GetMapping("/getAllClientsAndPts")
    public ResponseEntity<ClientsAndPtsListDto> getAllClientsAndPts(){
        return ResponseEntity.ok(consultantService.getAllClientsAndPts());
    }
}
