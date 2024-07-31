package com.example.backend.redDays;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.util.UUID;

@Entity
public class RedDays {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    LocalDate date;
}
