package com.example.backend.consultant;

import com.example.backend.client.TimekeeperClient;
import com.example.backend.client.dto.TimekeeperUserResponseDto;
import com.example.backend.consultant.dto.ConsultantResponseDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/consultants")
public class ConsultantController {

    private final TimekeeperClient client;

    public ConsultantController(TimekeeperClient client) {
        this.client = client;
    }

    @GetMapping
    public ResponseEntity<List<ConsultantResponseDto>> getConsultants() {
        List<ConsultantResponseDto> listOfConsultants = new ArrayList<>();
        listOfConsultants.add(new ConsultantResponseDto(
                UUID.randomUUID(),
                "Rune Ossler",
                "rune.ossler@appliedtechnology.se",
                "111-222-333"));
        return ResponseEntity.ok(listOfConsultants);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimekeeperUserResponseDto> getConsultant(@PathVariable Long id) {
        TimekeeperUserResponseDto timekeeperUserResponseDto = client.getUser(id);
        return ResponseEntity.ok(timekeeperUserResponseDto);
    }
}
