package com.WithDBConnection.VaccineManagementSystem.Repositories;

import com.WithDBConnection.VaccineManagementSystem.Models.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Integer> {
}
