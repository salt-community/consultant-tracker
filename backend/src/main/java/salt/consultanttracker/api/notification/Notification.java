package salt.consultanttracker.api.notification;

import salt.consultanttracker.api.responsiblept.ResponsiblePT;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String description;
    private LocalDateTime sentDate;

    @ManyToOne
    @JoinColumn(name = "saltUser_id", referencedColumnName = "id")
    private ResponsiblePT saltUser;
}
