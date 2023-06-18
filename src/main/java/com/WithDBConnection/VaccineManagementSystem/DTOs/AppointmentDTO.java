package com.WithDBConnection.VaccineManagementSystem.DTOs;

import lombok.Data;

import java.time.LocalTime;
import java.util.Date;

@Data
public class AppointmentDTO {
    private int doctorId;
    private int userId;
    private Date appointmentDate;
    private LocalTime appointmentTime;
}
