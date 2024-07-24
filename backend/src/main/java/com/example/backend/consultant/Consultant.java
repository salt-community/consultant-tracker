package com.example.backend.consultant;

import com.example.backend.saltUser.SaltUser;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class Consultant {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String fullName;
    private String email;
    private String phoneNumber;
    private Long timekeeperId;
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "saltUser_id", referencedColumnName = "id")
    @Nullable
    private SaltUser saltUser;

    public Consultant(UUID id,
                      String fullName,
                      String email,
                      String phoneNumber,
                      Long timekeeperId,
                      boolean active) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.timekeeperId = timekeeperId;
        this.active = active;
    }

    public Consultant() {
    }
}