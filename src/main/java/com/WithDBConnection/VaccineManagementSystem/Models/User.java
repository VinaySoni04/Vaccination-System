package com.WithDBConnection.VaccineManagementSystem.Models;

import com.WithDBConnection.VaccineManagementSystem.Enum.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Generated;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(name = "username")
    private String name;

    private int age;

    @Column(unique = true)
    private String emailId;

    private String mobileNo;

    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @JsonIgnore
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Dose dose;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Appointment> appointments=new ArrayList<>();
}
