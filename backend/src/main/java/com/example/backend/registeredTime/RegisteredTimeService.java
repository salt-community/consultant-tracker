package com.example.backend.registeredTime;

import com.example.backend.consultant.dto.ConsultantTimeDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class RegisteredTimeService {
    private final RegisteredTimeRepository registeredTimeRepository;

    public RegisteredTimeService(RegisteredTimeRepository registeredTimeRepository) {
        this.registeredTimeRepository = registeredTimeRepository;
    }

    public void saveConsultantTime(List<ConsultantTimeDto> consultantTimeDtoList) {
        AtomicReference<LocalDateTime> startTime = new AtomicReference<>(null);
        for (ConsultantTimeDto consultantTimeDto : consultantTimeDtoList) {
            if (startTime.get() == null) {
                startTime.set(consultantTimeDto.itemId().getStartDate());
            } else if (startTime.get().getDayOfMonth() == consultantTimeDto.itemId().getStartDate().getDayOfMonth() &&
                    startTime.get().getMonth() == consultantTimeDto.itemId().getStartDate().getMonth() &&
                    startTime.get().getYear() == consultantTimeDto.itemId().getStartDate().getYear()
            ) {
                continue;
            }
            registeredTimeRepository.save(new RegisteredTime(
                    new RegisteredTimeKey(consultantTimeDto.itemId().getConsultantId(),
                            consultantTimeDto.itemId().getStartDate()),
                    consultantTimeDto.dayType(),
                    consultantTimeDto.endDate().withHour(23).withMinute(59).withSecond(59),
                    8.0
            ));
        }
    }

    public List<RegisteredTime> getTimeByConsultantId(UUID id) {
        return registeredTimeRepository.findAllById_ConsultantId(id);
    }
}