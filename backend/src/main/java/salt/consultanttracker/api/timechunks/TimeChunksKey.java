package salt.consultanttracker.api.timechunks;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeChunksKey implements Serializable {
    @Column(name="consultantId")
    private UUID consultantId;

    @Column(name="start_date")
    private LocalDateTime startDate;
}