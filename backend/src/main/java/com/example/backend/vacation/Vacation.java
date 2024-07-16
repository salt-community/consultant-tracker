package com.example.backend.vacation;

import com.example.backend.consultant.Consultant;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class Vacation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private int availableDays;
    private int usedDays;

    @OneToOne
    @JoinColumn(name = "consultant_id", referencedColumnName = "id")
    private Consultant consultant;
}
