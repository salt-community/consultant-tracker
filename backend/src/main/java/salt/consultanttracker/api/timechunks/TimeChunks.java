package salt.consultanttracker.api.timechunks;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeChunks {
    @EmbeddedId
    private TimeChunksKey id;
    private String type;
    private LocalDateTime endDate;
    private int totalDays;
    private String projectName;
}
