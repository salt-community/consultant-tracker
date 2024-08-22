package com.example.backend.demo;

import com.example.backend.consultant.ConsultantService;
import com.example.backend.consultant.dto.ConsultantResponseDto;
import com.example.backend.consultant.dto.ConsultantResponseListDto;
import com.example.backend.demo.dto.DemoValuesDto;
import com.example.backend.registeredTime.dto.RegisteredTimeResponseDto;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@Data
public class DemoService {
    private final ConsultantService consultantService;

    List<String> demoPts = List.of("Stella Asplund", "Monica Sjöström");
    List<String> demoConsultants = List.of(
            "Leona Rehnquist",
            "Gabriella Tornquist",
            "Kira Lagerlöf",
            "Viggo Sanddahl",
            "André Palme",
            "Bertil Skarsgård",
            "Ulf Lindahl",
            "Alf Forsberg",
            "Faje Lindblad",
            "Denise Ceder");
    List<String> demoClients = List.of(
            "Globeworks",
            "Firemedia",
            "Karmaworth",
            "Explority",
            "Tigress Arts",
            "Desertpoint",
            "CB Cash",
            "Fjord Productions",
            "Sunex");

    public ConsultantResponseListDto getDemoConsultants(String name, List<String> client, List<String> pt) {
        ConsultantResponseListDto realData = consultantService.getAllConsultantDtos(0, 10, name, pt, client);
        List<ConsultantResponseDto> demoConsultants = realData.consultants()
                .stream().map(this::getDemoDataForConsultant).toList();
        return new ConsultantResponseListDto(0, 1, 10, demoConsultants);
    }

    private ConsultantResponseDto getDemoDataForConsultant(ConsultantResponseDto consultant) {
        DemoValuesDto demoValues = getRandomDemoValues();
        return new ConsultantResponseDto(
                consultant.id(),
                demoValues.name(),
                "random@email.com",
                null,
                demoValues.pt(),
                demoValues.client(),
                consultant.country(),
                consultant.totalDaysStatistics(),
                consultant.registeredTimeDtoList()
                        .stream().map(time -> new RegisteredTimeResponseDto(
                                time.registeredTimeId(),
                                time.startDate(),
                                time.endDate(),
                                time.type(),
                                demoValues.client(),
                                time.days()
                        )).toList()
        );
    }

    private DemoValuesDto getRandomDemoValues() {
        return new DemoValuesDto(getRandomName(), getRandomPt(), getRandomClient());
    }

    private String getRandomName() {
        int rand = new Random().nextInt(10);
        return demoConsultants.get(rand);
    }

    private String getRandomPt() {
        int rand = new Random().nextInt(2);
        return demoPts.get(rand);
    }

    private String getRandomClient() {
        int rand = new Random().nextInt(9);
        return demoClients.get(rand);
    }
}
