package com.example.backend.meetings_schedule;

import com.example.backend.consultant.Consultant;
import com.example.backend.saltUser.SaltUser;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class MeetingsSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String description;
    private LocalDateTime date;
    private String status;
    @ManyToOne
    @JoinColumn(name = "consultant_id", referencedColumnName = "id")
    private Consultant consultant;
    @ManyToOne
    @JoinColumn(name = "saltUser_id", referencedColumnName = "id")
    private SaltUser saltUser;

}
