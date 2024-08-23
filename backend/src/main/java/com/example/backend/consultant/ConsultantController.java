package com.example.backend.consultant;

import com.example.backend.consultant.dto.ClientsAndPtsListDto;
import com.example.backend.consultant.dto.ConsultantResponseDto;
import com.example.backend.consultant.dto.ConsultantResponseListDto;
import com.example.backend.demo.demoConsultant.DemoConsultantService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
    private final DemoConsultantService demoConsultantService;
    @Value("${app.mode}")
    private String appMode;

    @GetMapping
    public ResponseEntity<?> getConsultantsAndRegisteredTime(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "", required = false) String name,
            @RequestParam(defaultValue = "", required = false) List<String> client,
            @RequestParam(defaultValue = "", required = false) List<String> pt) {
        if ("demo".equalsIgnoreCase(appMode)) {
            System.out.println("IN DEMO MODE");
            return ResponseEntity.ok(demoConsultantService.getAllDemoConsultantDtos(page, pageSize, name, pt, client));
        }
        ConsultantResponseListDto consultantsResponse = consultantService.getAllConsultantDtos(page, pageSize, name, pt, client);
        return ResponseEntity.ok(consultantsResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getConsultantById(@PathVariable UUID id){
        if ("demo".equalsIgnoreCase(appMode)) {
            return ResponseEntity.ok(demoConsultantService.getDemoConsultantById(id));
        }
        return ResponseEntity.ok(consultantService.getConsultantById(id));
    }

    @GetMapping("/getAllClientsAndPts")
    public ResponseEntity<ClientsAndPtsListDto> getAllClientsAndPts(){
        if ("demo".equalsIgnoreCase(appMode)) {
            return ResponseEntity.ok(demoConsultantService.getAllDemoClientsAndPts());
        }
        return ResponseEntity.ok(consultantService.getAllClientsAndPts());
    }
}
