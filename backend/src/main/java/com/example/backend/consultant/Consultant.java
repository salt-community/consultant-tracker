package com.example.backend.consultant;

import com.example.backend.saltUser.SaltUser;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Consultant {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String fullName;
    private String email;
    private Long timekeeperId;
    private String notionId;
    private boolean active;
    private String client;
    private String country;

    @ManyToOne
    @JoinColumn(name = "saltUser_id", referencedColumnName = "id")
    @Nullable
    private SaltUser saltUser;
}