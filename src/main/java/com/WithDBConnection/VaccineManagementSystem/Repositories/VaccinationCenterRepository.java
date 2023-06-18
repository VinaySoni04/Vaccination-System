package com.WithDBConnection.VaccineManagementSystem.Repositories;

import com.WithDBConnection.VaccineManagementSystem.Models.VaccinationCenter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VaccinationCenterRepository extends JpaRepository<VaccinationCenter,Integer> {
}
