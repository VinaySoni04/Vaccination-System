package com.WithDBConnection.VaccineManagementSystem.Repositories;

import com.WithDBConnection.VaccineManagementSystem.Enum.Gender;
import com.WithDBConnection.VaccineManagementSystem.Models.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor,Integer> {
    Doctor findByEmailId(String emailId);
}
