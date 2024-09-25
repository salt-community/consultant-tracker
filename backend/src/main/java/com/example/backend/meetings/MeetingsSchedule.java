package com.example.backend.meetings;


import com.example.backend.saltuser.SaltUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeetingsSchedule {

    @EmbeddedId
    private MeetingsScheduleKey id;

    private LocalDate date;
    private String status;

    @ManyToOne
    @JoinColumn(name = "saltUser_id", referencedColumnName = "id")
    private SaltUser saltUser;

}
