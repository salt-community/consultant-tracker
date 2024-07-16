package com.example.backend.absence;

import com.example.backend.consultant.Consultant;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class Absence {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @ManyToOne
    @JoinColumn(name = "consultant_id", referencedColumnName = "id")
    private Consultant consultant;
}
