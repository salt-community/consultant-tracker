package salt.consultanttracker.api.demo;

import salt.consultanttracker.api.consultant.dto.ClientsListDto;
import salt.consultanttracker.api.consultant.dto.TotalDaysStatisticsDto;
import salt.consultanttracker.api.reddays.CountryCode;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static salt.consultanttracker.api.utils.Country.SWEDEN;

public record DemoSingleConsultantResponseListDto(UUID id,
                                                  String fullName,
                                                  String email,
                                                  String responsiblePt,
                                                  String client,
                                                  String country,
                                                  TotalDaysStatisticsDto totalDaysStatistics,
                                                  List<ClientsListDto> clientsListDto) {

    public static DemoSingleConsultantResponseListDto toDto(
            DemoConsultant consultant,
            TotalDaysStatisticsDto totalDaysStatistics,
            List<ClientsListDto> clientsListDto) {
        return new DemoSingleConsultantResponseListDto(
                consultant.getId(),
                consultant.getFullName(),
                consultant.getEmail(),
                consultant.getResponsiblePT(),
                consultant.getClient(),
                consultant.getCountry().equals(SWEDEN.country) ? CountryCode.SE.countryCode : CountryCode.NO.countryCode,
                totalDaysStatistics,
                List.of(new ClientsListDto(consultant.getClient(), LocalDate.now(), LocalDate.now()))
        );
    }
}
