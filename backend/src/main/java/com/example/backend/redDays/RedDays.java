package com.example.backend.redDays;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RedDays {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    LocalDate date;
    String name;
    String country;

}
