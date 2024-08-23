package com.example.backend.demo.demoConsultant;

import jakarta.annotation.Nullable;
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
    private String phoneNumber;
    private Long timekeeperId;
    private boolean active;
    private String client;
    private String responsiblePT;
    private String country;
}
