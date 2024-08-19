package com.example.backend.consultant;

import com.example.backend.consultant.dto.ClientsAndPtsListDto;
import com.example.backend.consultant.dto.ConsultantResponseDto;
import com.example.backend.consultant.dto.ConsultantResponseListDto;
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

    @GetMapping("/{id}")
    public ResponseEntity<ConsultantResponseDto> getConsultantById(@PathVariable UUID id){
        return ResponseEntity.ok(consultantService.getConsultantById(id));
    }

    @GetMapping("/getAllClientsAndPts")
    public ResponseEntity<ClientsAndPtsListDto> getAllClientsAndPts(){
        return ResponseEntity.ok(consultantService.getAllClientsAndPts());
    }
}
