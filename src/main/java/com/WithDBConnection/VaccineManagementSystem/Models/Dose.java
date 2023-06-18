package com.WithDBConnection.VaccineManagementSystem.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "dose")
@Data
public class Dose {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // Primary Key

    @Column(unique = true)
    private String doseId; // Always unique

    @CreationTimestamp  // Stores the current time
    private Date vaccinationDate;

    @JsonIgnore
    @OneToOne
    @JoinColumn
    private User user;
}
