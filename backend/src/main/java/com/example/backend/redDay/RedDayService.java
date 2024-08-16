package com.example.backend.redDay;

import com.example.backend.client.nager.NagerClient;
import com.example.backend.client.nager.dto.RedDaysFromNagerDto;
import com.example.backend.consultant.ConsultantService;
import com.example.backend.redDay.dto.RedDaysResponseDto;
import com.example.backend.utils.Utilities;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.backend.redDay.CountryCode.NO;
import static com.example.backend.redDay.CountryCode.SE;


@Service
public class RedDayService {
    private final RedDayRepository redDaysRepository;
    private final NagerClient workingDaysClient;
    private final ConsultantService consultantService;

    public RedDayService(@Lazy ConsultantService consultantService,
                         RedDayRepository redDaysRepository,
                         NagerClient workingDaysClient
                          ) {
        this.consultantService = consultantService;
        this.redDaysRepository = redDaysRepository;
        this.workingDaysClient = workingDaysClient;
    }
    //-----------------------------COVERED BY TESTS ---------------------------------
    public List<LocalDate> getRedDays(String countryCode) {
        List<RedDay> allDates = redDaysRepository.findAllByCountry(countryCode);
        return allDates.stream().map(el -> el.date).toList();
    }

    public RedDaysResponseDto getAllRedDays(){
        List<LocalDate> redDaysSE = getRedDays(SE.countryCode);
        List<LocalDate> redDaysNO = getRedDays(NO.countryCode);
        return new RedDaysResponseDto(redDaysSE, redDaysNO);
    }

    public List<RedDay> getRedDaysFromNager(int startYear, int endYear) {
        List<RedDay> savedRedDaysDB = new ArrayList<>();
        for (int i = 0; startYear + i < endYear; i++) {
            List<RedDaysFromNagerDto> currentYearRedDaysArray = workingDaysClient.getRedDaysPerYear(startYear + i, new String[]{"SE", "NO"});
            savedRedDaysDB.addAll(saveRedDays(currentYearRedDaysArray));
        }
        return savedRedDaysDB;
    }

    private List<RedDay> saveRedDays(List<RedDaysFromNagerDto> redDaysArray) {
        List<RedDay> savedRedDays = new ArrayList<>();
        for (RedDaysFromNagerDto redDays : redDaysArray) {
            RedDay save = redDaysRepository.save(new RedDay(UUID.randomUUID(), redDays.date(), redDays.name(), redDays.countryCode()));
            savedRedDays.add(save);
        }
        return savedRedDays;
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
