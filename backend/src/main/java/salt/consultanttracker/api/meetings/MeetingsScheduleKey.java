package salt.consultanttracker.api.meetings;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeetingsScheduleKey implements Serializable {

    @Column(name="consultantId")
    private UUID consultantId;

    @Column(name="title")
    private Meetings title;
}
