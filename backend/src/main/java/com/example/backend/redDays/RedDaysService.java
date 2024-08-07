package com.example.backend.redDays;

import com.example.backend.client.nager.NagerClient;
import com.example.backend.client.nager.dto.RedDaysFromNagerDto;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@Data
public class RedDaysService {
    private final RedDaysRepository redDaysRepository;
    private final NagerClient workingDaysClient;

    public List<LocalDate> getRedDays(String countryCode) {
        List<RedDays> allDates = redDaysRepository.findAllByCountry(countryCode);
        return allDates.stream().map(el -> el.date).toList();
    }

    public void getRedDaysFromNager() {
        int year2018 = 2018;
        int year2030 = 2030;
        for (int i = 0; year2018 + i < year2030; i++) {
            List<RedDaysFromNagerDto> currentYearRedDaysArray = workingDaysClient.getRedDaysPerYear(year2018 + i, new String[]{"SE", "NO"});
            saveRedDays(currentYearRedDaysArray);
        }
    }

    private void saveRedDays(List<RedDaysFromNagerDto> redDaysArray) {
        for (RedDaysFromNagerDto redDays : redDaysArray) {
            redDaysRepository.save(new RedDays(UUID.randomUUID(), redDays.date(), redDays.name(), redDays.countryCode()));
        }
    }
}
