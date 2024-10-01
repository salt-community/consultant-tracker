package salt.consultanttracker.api.reddays;

import salt.consultanttracker.api.client.nager.NagerClient;
import salt.consultanttracker.api.client.nager.dto.RedDaysFromNagerDto;
import salt.consultanttracker.api.consultant.ConsultantService;
import salt.consultanttracker.api.exceptions.ExternalAPIException;
import salt.consultanttracker.api.reddays.dto.RedDaysResponseDto;
import salt.consultanttracker.api.utils.Country;
import salt.consultanttracker.api.utils.Utilities;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import static salt.consultanttracker.api.utils.Country.NO;
import static salt.consultanttracker.api.utils.Country.SE;
import static salt.consultanttracker.api.utils.Country.NORWAY;


@Service
public class RedDayService {
    private final RedDayRepository redDaysRepository;
    private final NagerClient nagerClient;
    private final ConsultantService consultantService;
    private static final Logger LOGGER = Logger.getLogger(RedDayService.class.getName());

    public RedDayService(@Lazy ConsultantService consultantService,
                         RedDayRepository redDaysRepository,
                         NagerClient workingDaysClient
                          ) {
        this.consultantService = consultantService;
        this.redDaysRepository = redDaysRepository;
        this.nagerClient = workingDaysClient;
    }
    //-----------------------------COVERED BY TESTS ---------------------------------
    public List<LocalDate> getRedDays(String countryCode) {
        List<RedDay> allDates = redDaysRepository.findAllById_CountryCode(countryCode);
        return allDates.stream().map(el -> el.getId().getDate()).toList();
    }

    public RedDaysResponseDto getAllRedDays(){
        List<LocalDate> redDaysSE = getRedDays(SE.country);
        List<LocalDate> redDaysNO = getRedDays(NO.country);
        return new RedDaysResponseDto(redDaysSE, redDaysNO);
    }


    private String getCountryCode(UUID consultantId) {
        return consultantService.getCountryCodeByConsultantId(consultantId).equals(NORWAY.country) ? NO.country : SE.country;
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

//    @PostConstruct
    @Scheduled(cron="0 0 0 1 1 *", zone = "Europe/Stockholm")
public void getRedDaysFromNager() throws ExternalAPIException {
        LOGGER.info("Fetching red days from Nager");
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
            List<RedDaysFromNagerDto> currentYearRedDaysArray = nagerClient.getRedDaysPerYear(saltStartYear + i, new String[]{"SE", "NO"});
            //TODO handle in case there is not response from nager;
            saveRedDays(currentYearRedDaysArray);
        }
        LOGGER.info("Red days from Nager fetched");
    }


    private void saveRedDays(List<RedDaysFromNagerDto> redDaysArray) {
        for (RedDaysFromNagerDto redDays : redDaysArray) {
            redDaysRepository.save(new RedDay(new RedDayKey(redDays.date(), redDays.countryCode()), redDays.name()));
        }
    }
}
