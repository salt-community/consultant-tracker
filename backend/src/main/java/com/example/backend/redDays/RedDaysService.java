package com.example.backend.redDays;

import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Data
public class RedDaysService {
    private final RedDaysRepository redDaysRepository;

    public List<LocalDate> getRedDays(){
        List<RedDays> allDates = redDaysRepository.findAll();
        List<LocalDate> list = allDates.stream().map(el -> el.date).toList();
        return allDates.stream().map(el -> el.date).toList();
    }
}
