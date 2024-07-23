package com.example.backend.registeredTime;

import com.example.backend.consultant.Consultant;
import com.example.backend.consultant.ConsultantService;
import com.example.backend.consultant.dto.ConsultantTimeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static com.example.backend.client.Activity.CONSULTANCY_TIME;

@Service
public class RegisteredTimeService {
    private final RegisteredTimeRepository registeredTimeRepository;
    private final ConsultantService consultantService;

    public RegisteredTimeService(RegisteredTimeRepository registeredTimeRepository,
                                 @Lazy ConsultantService consultantService) {
        this.registeredTimeRepository = registeredTimeRepository;
        this.consultantService = consultantService;
    }

    public void saveConsultantTime(List<ConsultantTimeDto> consultantTimeDtoList) {
        List<ConsultantTimeDto> listOfRegisteredTime = new ArrayList<>();
        AtomicReference<String> activityTypePrev = new AtomicReference<>(CONSULTANCY_TIME.activity);
        AtomicReference<LocalDateTime> startTime = new AtomicReference<>(null);
        AtomicReference<LocalDateTime> endTime = new AtomicReference<>();
        AtomicInteger countDays = new AtomicInteger();
        for(int i=0; i < consultantTimeDtoList.size(); i++){
            if (consultantTimeDtoList.get(i).dayType().equals(activityTypePrev.get())) {
                if (startTime.get() == null) {
                    startTime.set(consultantTimeDtoList.get(i).itemId().getStartDate());
                }
                else{
                    if (endTime.get().getDayOfMonth() != consultantTimeDtoList.get(i).itemId().getStartDate().getDayOfMonth() ||
                            endTime.get().getMonth() != consultantTimeDtoList.get(i).itemId().getStartDate().getMonth() ||
                            endTime.get().getYear() != consultantTimeDtoList.get(i).itemId().getStartDate().getYear()
                    ) {
                        countDays.getAndIncrement();
                    }
                }
                endTime.set(consultantTimeDtoList.get(i).endDate());
                activityTypePrev.set(consultantTimeDtoList.get(i).dayType());
                if (listOfRegisteredTime.isEmpty() && i == consultantTimeDtoList.size()-1) {
                    listOfRegisteredTime.add(new ConsultantTimeDto(
                            new RegisteredTimeKey(consultantTimeDtoList.get(i).itemId().getConsultantId(),startTime.get()),
                            endTime.get(),
                            activityTypePrev.get(),
                            countDays.get()));
                }
            } else {
                listOfRegisteredTime.add(new ConsultantTimeDto(
                        new RegisteredTimeKey(consultantTimeDtoList.get(i).itemId().getConsultantId(),startTime.get()),
                        endTime.get(),
                        activityTypePrev.get(),
                        countDays.get()));
                startTime.set(consultantTimeDtoList.get(i).itemId().getStartDate());
                countDays.set(0);
            }

        }
        saveRegisteredTimeForConsultant(listOfRegisteredTime);
    }

    private void saveRegisteredTimeForConsultant(List<ConsultantTimeDto> listOfRegisteredTime) {
        listOfRegisteredTime.forEach(el -> {
            Consultant consultant = consultantService.findConsultantById(el.itemId().getConsultantId());
            registeredTimeRepository.save(
                    new RegisteredTime(
                            new RegisteredTimeKey(el.itemId().getConsultantId(), el.itemId().getStartDate()),
                            el.dayType(),
//                            el.startDate(),
                            el.endDate(),
                            el.totalDays()
//                            consultant
                    ));
        });
    }
    public List<RegisteredTime> getTimeByConsultantId(UUID id){
        return registeredTimeRepository.findAllById_ConsultantId(id);
    }
}
