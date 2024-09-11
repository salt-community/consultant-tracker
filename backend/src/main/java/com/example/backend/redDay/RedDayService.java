package com.example.backend.redDay;

import com.example.backend.client.nager.NagerClient;
import com.example.backend.client.nager.dto.RedDaysFromNagerDto;
import com.example.backend.consultant.ConsultantService;
import com.example.backend.redDay.dto.RedDaysResponseDto;
import com.example.backend.utils.Utilities;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

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
        List<RedDay> allDates = redDaysRepository.findAllById_CountryCode(countryCode);
        return allDates.stream().map(el -> el.getId().getDate()).toList();
    }

    public RedDaysResponseDto getAllRedDays(){
        List<LocalDate> redDaysSE = getRedDays(SE.countryCode);
        List<LocalDate> redDaysNO = getRedDays(NO.countryCode);
        return new RedDaysResponseDto(redDaysSE, redDaysNO);
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

    @Scheduled(cron = "0 55 09 * * *", zone = "Europe/Stockholm")
    public void test1 (){
        Logger logger = Logger.getLogger(RedDayService.class.getName());
        logger.info("test1, 09:55");
    }

    @Scheduled(cron = "0 55 10 * * *", zone = "Europe/Stockholm")
    public void test2 (){
        Logger logger = Logger.getLogger(RedDayService.class.getName());
        logger.info("test2, 10:55");
    }

    @Scheduled(cron = "0 55 12 * * *", zone = "Europe/Stockholm")
    public void test3 (){
        Logger logger = Logger.getLogger(RedDayService.class.getName());
        logger.info("test3, 12:55");
    }

    @Scheduled(cron = "0 55 13 * * *", zone = "Europe/Stockholm")
    public void test4 (){
        Logger logger = Logger.getLogger(RedDayService.class.getName());
        logger.info("test4, 13:55");
    }

//    @PostConstruct
    @Scheduled(cron="0 0 0 1 1 *")
public void getRedDaysFromNager() {
        var saltStartYear = 2018;
//        TODO ask about value of that part - refactor?
        RedDay latestDateDB = redDaysRepository.findFirstByOrderById_DateDesc();
        if(latestDateDB != null ){
            int latestYearDB = latestDateDB.getId().getDate().getYear();
            if(latestYearDB== Year.now().getValue()+1){
                return;
            }
            saltStartYear = latestYearDB+1;
        }
        //        TODO ask about value of that part
        for (int i = 0; saltStartYear + i <= Year.now().getValue()+1; i++) {
            List<RedDaysFromNagerDto> currentYearRedDaysArray = workingDaysClient.getRedDaysPerYear(saltStartYear + i, new String[]{"SE", "NO"});
            //TODO handle in case there is not response from nager;
            saveRedDays(currentYearRedDaysArray);
        }
    }


    private void saveRedDays(List<RedDaysFromNagerDto> redDaysArray) {
        for (RedDaysFromNagerDto redDays : redDaysArray) {
            redDaysRepository.save(new RedDay(new RedDayKey(redDays.date(), redDays.countryCode()), redDays.name()));
        }
    }
}
