package salt.consultanttracker.api.consultant;

import salt.consultanttracker.api.responsiblept.ResponsiblePT;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Consultant {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String fullName;
    private String email;
    private Long timekeeperId;
    @Nullable
    private UUID notionId;
    private boolean active;
    private String client;
    private String country;

    @ManyToOne
    @JoinColumn(name = "responsiblePT_id", referencedColumnName = "id")
    @Nullable
    private ResponsiblePT responsiblePT;
}