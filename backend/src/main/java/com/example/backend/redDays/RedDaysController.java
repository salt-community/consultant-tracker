package com.example.backend.redDays;

import com.example.backend.redDays.dto.RedDaysResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/redDays")
@RequiredArgsConstructor
@CrossOrigin
public class RedDaysController {
    private final RedDaysService redDaysService;

    @GetMapping
    public ResponseEntity<RedDaysResponseDto> getAllRedDays(){
        return ResponseEntity.ok(redDaysService.getAllRedDays());
    }
}
