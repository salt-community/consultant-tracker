package com.example.backend.consultant;
import com.example.backend.saltUser.SaltUser;
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

//    private Client client;
    @ManyToOne
    @JoinColumn(name = "saltUser_id", referencedColumnName = "id")
    private SaltUser saltUser;
//    private Contract contract;
}