package salt.consultanttracker.api.registeredtime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisteredTime {

    @EmbeddedId
    private RegisteredTimeKey id;

    private String type;

    private LocalDateTime endDate;
    private double totalHours;
    private String projectName;
}
