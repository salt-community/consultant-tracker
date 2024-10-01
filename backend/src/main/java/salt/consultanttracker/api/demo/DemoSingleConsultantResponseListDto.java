package salt.consultanttracker.api.demo;

import salt.consultanttracker.api.consultant.dto.ClientsListDto;
import salt.consultanttracker.api.consultant.dto.TotalDaysStatisticsDto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static salt.consultanttracker.api.utils.Country.*;

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
                consultant.getCountry().equals(SWEDEN.country) ? SE.country : NO.country,
                totalDaysStatistics,
                List.of(new ClientsListDto(consultant.getClient(), LocalDate.now(), LocalDate.now()))
        );
    }
}
