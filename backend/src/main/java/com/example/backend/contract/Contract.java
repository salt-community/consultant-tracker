package com.example.backend.contract;

import com.example.backend.consultant.Consultant;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String client;

    @OneToOne
    @JoinColumn(name = "consultant_id", referencedColumnName = "id")
    private Consultant consultant;



}
