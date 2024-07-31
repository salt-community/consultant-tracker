package com.example.backend.redDays;

import com.example.backend.client.dagsmart.DagsmartClient;
import com.example.backend.client.dagsmart.dto.RedDaysFromDagsmartDto;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static com.example.backend.redDays.RedDaysExclude.NEW_YEAR_EVE;
import static com.example.backend.redDays.RedDaysExclude.WHIT_SUN_EVE;

@Service
@Data
public class RedDaysService {
    private final RedDaysRepository redDaysRepository;
    private final DagsmartClient dagsmartClient;

    public List<LocalDate> getRedDays() {
        List<RedDays> allDates = redDaysRepository.findAll();
        return allDates.stream().map(el -> el.date).toList();
    }

    public void getRedDaysFromDagsmart() {
        int prevYear = LocalDate.now().getYear() - 1;
        for (int i = 0; i < 3; i++) {
            RedDaysFromDagsmartDto[] currentYearRedDaysArray = dagsmartClient.getRedDaysPerYear(prevYear + i);
            saveRedDays(currentYearRedDaysArray);
        }
    }

    private void saveRedDays(RedDaysFromDagsmartDto[] redDaysArray) {
        for (RedDaysFromDagsmartDto redDays : redDaysArray) {
            if (!redDays.code().equals(WHIT_SUN_EVE.name)
                    && !redDays.code().equals(NEW_YEAR_EVE.name))
                redDaysRepository.save(new RedDays(UUID.randomUUID(), LocalDate.parse(redDays.date()), redDays.name().en()));
        }
    }
}
