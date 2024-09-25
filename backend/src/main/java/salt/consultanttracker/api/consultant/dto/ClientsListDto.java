package salt.consultanttracker.api.consultant.dto;

import java.time.LocalDate;

public record ClientsListDto(String name, LocalDate startDate, LocalDate endDate) {
}
