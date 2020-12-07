package com.example.servicehealthtracker.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@With
@NoArgsConstructor
@AllArgsConstructor
public class Service {
    @Id
    @GeneratedValue
    private Long id;
    private String url;
    @Enumerated(EnumType.STRING)
    private Status status;
    private String description;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
