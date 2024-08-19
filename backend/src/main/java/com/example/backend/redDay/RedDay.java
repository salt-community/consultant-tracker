package com.example.backend.redDay;

import com.example.backend.registeredTime.RegisteredTimeKey;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class RedDay {
    @EmbeddedId
    private RedDayKey id;
    String name;
}
