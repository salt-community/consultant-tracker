package com.example.backend.timechunks;

import com.example.backend.consultant.Consultant;
import com.example.backend.consultant.ConsultantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/timeChunks")
@RequiredArgsConstructor
@CrossOrigin
public class TimeChunkController {
    private final TimeChunksService timeChunksService;
    private final ConsultantService consultantService;

    @GetMapping
    public void saveTimeChunks(){
        Consultant consultant = consultantService.getConsultantByIdAndReturnConsultant(UUID.fromString("7b6c9dbd-f347-4ccd-8d46-885849fdb8d0"));
        timeChunksService.saveTimeChunksForAllConsultants(new ArrayList<>(List.of(consultant)));
    }
}
