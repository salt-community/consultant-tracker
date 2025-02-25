package salt.consultanttracker.api.reddays;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedDayKey implements Serializable {
    @Column(name="redDayDate")
    private LocalDate date;

    @Column(name="redDayCountry")
    private String countryCode;

}