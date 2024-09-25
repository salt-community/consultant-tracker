package salt.consultanttracker.api.client.nager.dto;

import java.time.LocalDate;

public record RedDaysFromNagerDto(LocalDate date, String name, String countryCode) {
}
