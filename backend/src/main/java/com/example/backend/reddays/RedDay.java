package com.example.backend.reddays;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class RedDay {
    @EmbeddedId
    private RedDayKey id;
    String name;
}
