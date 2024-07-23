package com.example.backend.registeredTime;

import com.example.backend.consultant.Consultant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisteredTime {

    @EmbeddedId
    private RegisteredTimeKey id;

    private String type;

    @Column(name="start_date")
    private LocalDateTime startDate;

    private LocalDateTime endDate;
    private int totalDays;

    @ManyToOne
    @JoinColumn(name = "consultantId", referencedColumnName = "id")
    private Consultant consultant;
}
