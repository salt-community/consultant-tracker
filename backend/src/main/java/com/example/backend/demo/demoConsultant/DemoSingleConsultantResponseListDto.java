package com.example.backend.demo.demoConsultant;

import com.example.backend.consultant.dto.ClientsListDto;
import com.example.backend.consultant.dto.TotalDaysStatisticsDto;
import com.example.backend.redDay.CountryCode;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static com.example.backend.utils.Country.SWEDEN;

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
