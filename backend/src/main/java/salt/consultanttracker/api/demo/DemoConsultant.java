package salt.consultanttracker.api.demo;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name="demo_consultant")
public class DemoConsultant {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String fullName;
    private String email;
    private Long timekeeperId;
    private String notionId;
    private boolean active;
    private String client;
    private String responsiblePT;
    private String country;
}
