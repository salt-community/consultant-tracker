package salt.consultanttracker.api.consultant;

import salt.consultanttracker.api.consultant.dto.ClientsAndPtsListDto;
import salt.consultanttracker.api.consultant.dto.ConsultantResponseDto;
import salt.consultanttracker.api.consultant.dto.ConsultantResponseListDto;
import salt.consultanttracker.api.consultant.dto.TotalDaysStatisticsDto;
import salt.consultanttracker.api.registeredtime.dto.RegisteredTimeResponseDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class MockedConsultantController {

    public static ConsultantResponseDto createMockedConsultantResponseDto(String name, String email, String pt, String client){
        DateTimeFormatter formatterSeconds = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return new ConsultantResponseDto(UUID.fromString("53b6a53f-c71b-4264-a17a-c6a3e2489273"),
                name,
                email,
                pt,
                client,
                "Sverige",
                new TotalDaysStatisticsDto(10, 10, 0,0,0,0,0, 80, 80),
                List.of(new RegisteredTimeResponseDto(UUID.fromString("1bc08a49-e27c-499a-8bb2-c5b70c646fca"),
                                LocalDateTime.parse("2024-02-01 00:00:00", formatterSeconds),
                                LocalDateTime.parse("2024-02-05 00:00:00", formatterSeconds),
                                "Konsult-tid",
                                "H&M",
                                10
                        )
                ));
    }
    public static ConsultantResponseListDto createMockedConsultantResponseListSize1Dto(){
        return new ConsultantResponseListDto(0,
                4,
                10,
                List.of(createMockedConsultantResponseDto("Michael Lynn", "michael.lynn@gmail.com", "Helga Rasmussen","Swedbank")));
    }
    public static ConsultantResponseListDto createMockedConsultantResponseListSize11Dto(){
        return new ConsultantResponseListDto(0,
                4,
                10,
                List.of(createMockedConsultantResponseDto("Herman Gustav", "herman.gustav@gmail.com", "Emmy Liselotte","H&M")));
    }
    public static ConsultantResponseListDto createMockedConsultantResponseListSize2Dto(){
        return new ConsultantResponseListDto(0,
                4,
                10,
                List.of(createMockedConsultantResponseDto("Herman Gustav", "herman.gustav@gmail.com", "Emmy Liselotte","H&M"),
                        createMockedConsultantResponseDto("Michael Lynn", "michael.lynn@gmail.com", "Helga Rasmussen","Swedbank")));
    }

   public static ClientsAndPtsListDto createMockedClientsAndPtsResponseList(){
        return new ClientsAndPtsListDto(Set.of("H&M", "Swedbank"), Set.of("Emmy Liselotte","Helga Rasmussen", "Loretta Fischer"));
   }
}
