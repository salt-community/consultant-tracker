package com.example.backend.registeredTime;

import com.example.backend.consultant.dto.ConsultantTimeDto;
import com.example.backend.registeredTime.dto.RegisteredTimeResponseDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static com.example.backend.client.Activity.CONSULTANCY_TIME;

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
                    8.0,
                    consultantTimeDto.projectName()
            ));
        }
    }

    public List<RegisteredTime> getTimeByConsultantId(UUID id) {
        return registeredTimeRepository.findAllById_ConsultantIdOrderById_StartDateAsc(id);
    }

    public List<RegisteredTime> getFirstAndLastDateByConsultantId(UUID consultantId) {
        RegisteredTime startDate = registeredTimeRepository.findFirstById_ConsultantIdOrderById_StartDateAsc(consultantId);
        RegisteredTime endDate = registeredTimeRepository.findFirstById_ConsultantIdOrderByEndDateDesc(consultantId);
        return new ArrayList<>(Arrays.asList(startDate, endDate));
    }

//    public RegisteredTimeResponseDto getRemainingConsultancyTimeByConsultantId(UUID consultantId) {
//        LocalDateTime lastRegisteredDate = registeredTimeRepository.findFirstById_ConsultantIdOrderByEndDateDesc(consultantId).getEndDate();
//        LocalDateTime startDate = lastRegisteredDate.plusDays(1).withHour(0).withMinute(0).withSecond(0);
//        LocalDateTime estimatedEndDate = getEstimatedConsultancyEndDate(consultantId, startDate);
//        if (estimatedEndDate == startDate) {
//            return null;
//        }
//        return new RegisteredTimeResponseDto(
//                UUID.randomUUID(),
//                startDate,
//                estimatedEndDate,
//                "Remaining Days");
//    }

    private LocalDateTime getEstimatedConsultancyEndDate(UUID consultantId, LocalDateTime startDate) {
        final int REQUIRED_HOURS = 2024;
        int countOfWorkedDays = registeredTimeRepository.countAllById_ConsultantIdAndTypeIs(consultantId, CONSULTANCY_TIME.activity);
        int remainingConsultancyDays = REQUIRED_HOURS / 8 - countOfWorkedDays;
//        System.out.println("remainingConsultancyDays = " + remainingConsultancyDays);
        if (remainingConsultancyDays <= 0) {
            return startDate;
        }
        return accountForNonWorkingDays(startDate, remainingConsultancyDays);
    }

    private LocalDateTime accountForNonWorkingDays(LocalDateTime startDate, int remainingDays) {
        if (remainingDays <= 0) {
            return startDate;
        }
        int daysCountDown = remainingDays;
        int i = 0;
        while (daysCountDown > 0) {

            if (!isWeekend(startDate.plusDays(i).getDayOfWeek().getValue()) /*&& !isRedDay(startDate.plusDays(i))*/) {
                daysCountDown--;
            }
            i++;
        }
        LocalDateTime endingDate = startDate.plusDays(i).minusSeconds(1L);
        return endingDate;
    }

    public boolean isWeekend(int day) {
        final int SATURDAY = 6;
        final int SUNDAY = 7;
        return day == SATURDAY || day == SUNDAY;
    }
}