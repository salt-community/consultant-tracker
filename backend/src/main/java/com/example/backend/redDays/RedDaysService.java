package com.example.backend.redDays;

import com.example.backend.client.nager.NagerClient;
import com.example.backend.client.nager.dto.RedDaysFromNagerDto;
import com.example.backend.consultant.ConsultantService;
import com.example.backend.utils.Utilities;
import lombok.Data;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Data
public class RedDaysService {
    private final RedDaysRepository redDaysRepository;
    private final NagerClient workingDaysClient;
    private final ConsultantService consultantService;

    public RedDaysService(@Lazy ConsultantService consultantService,
                          RedDaysRepository redDaysRepository,
                          NagerClient workingDaysClient
                          ) {
        this.consultantService = consultantService;
        this.redDaysRepository = redDaysRepository;
        this.workingDaysClient = workingDaysClient;
    }

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
    private String getCountryCode(UUID consultantId) {
        return consultantService.getCountryCodeByConsultantId(consultantId).equals("Sverige") ? "SE" : "NO";
    }

    public boolean isRedDay(LocalDate date, UUID consultantId) {
        List<LocalDate> redDays = getRedDays(getCountryCode(consultantId));
        return redDays.contains(date);
    }

    public LocalDateTime removeNonWorkingDays(LocalDateTime startDate, int remainingDays, UUID consultantId) {
        if (remainingDays <= 0) {
            return startDate;
        }
        int daysCountDown = remainingDays;
        int i = 0;
        while (daysCountDown > 0) {
            if (!Utilities.isWeekend(startDate.plusDays(i).getDayOfWeek().getValue())
                    && !isRedDay(LocalDate.from(startDate.plusDays(i)), consultantId)) {
                daysCountDown--;
            }
            i++;
        }
        return startDate.plusDays(i).minusSeconds(1L);
    }

    public int checkRedDaysOrWeekend(Long daysBetween, LocalDate dateBefore, UUID consultantId, String variant) {
        int nonWorkingDays = 0;
        for (long j = 1; j < daysBetween; j++) {
            var dateToCheck = dateBefore.plusDays(j);
            if (Utilities.isWeekend(dateToCheck.getDayOfWeek().getValue())
                    || isRedDay(dateToCheck, consultantId)) {
                nonWorkingDays++;
                continue;
            }
            if (variant.equals("single check")) {
                j = daysBetween;
            }
        }
        return nonWorkingDays;
    }
}
