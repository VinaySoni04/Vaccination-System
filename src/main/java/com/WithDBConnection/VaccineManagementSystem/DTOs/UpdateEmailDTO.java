package com.WithDBConnection.VaccineManagementSystem.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEmailDTO {
    private int userId;
    private String newEmailId;
}
