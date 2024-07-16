package com.example.backend.consultant;

import com.example.backend.consultant.dto.ConsultantResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/consultants")
public class ConsultantController {

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
}
