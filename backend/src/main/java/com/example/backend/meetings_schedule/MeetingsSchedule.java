package com.example.backend.meetings_schedule;

import com.example.backend.consultant.Consultant;
import com.example.backend.saltUser.SaltUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeetingsSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.ORDINAL)
    private Meetings title;
    private LocalDate date;
    private String status;

    @ManyToOne
    @JoinColumn(name = "consultant_id", referencedColumnName = "id")
    private Consultant consultant;
    @ManyToOne
    @JoinColumn(name = "saltUser_id", referencedColumnName = "id")
    private SaltUser saltUser;

}
