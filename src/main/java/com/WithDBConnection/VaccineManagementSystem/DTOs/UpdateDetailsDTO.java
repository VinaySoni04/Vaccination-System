package com.WithDBConnection.VaccineManagementSystem.DTOs;

import com.WithDBConnection.VaccineManagementSystem.Enum.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDetailsDTO {
    private String newName;
    private int newAge;
    @Enumerated(EnumType.STRING)
    private Gender newGender;
    private String emailId;
}
