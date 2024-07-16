package com.example.backend.notification;

import com.example.backend.saltUser.SaltUser;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String description;
    private LocalDateTime sentDate;

    @ManyToOne
    @JoinColumn(name = "saltUser_id", referencedColumnName = "id")
    private SaltUser saltUser;

}
