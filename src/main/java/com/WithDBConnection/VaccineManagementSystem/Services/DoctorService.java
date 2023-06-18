package com.WithDBConnection.VaccineManagementSystem.Services;

import com.WithDBConnection.VaccineManagementSystem.DTOs.AssociateDoctorDTO;
import com.WithDBConnection.VaccineManagementSystem.Exceptions.CenterNotFoundException;
import com.WithDBConnection.VaccineManagementSystem.Exceptions.DoctorAlreadyExistsException;
import com.WithDBConnection.VaccineManagementSystem.Exceptions.EmailIdEmptyException;
import com.WithDBConnection.VaccineManagementSystem.Models.Doctor;
import com.WithDBConnection.VaccineManagementSystem.Models.VaccinationCenter;
import com.WithDBConnection.VaccineManagementSystem.Repositories.DoctorRepository;
import com.WithDBConnection.VaccineManagementSystem.Repositories.VaccinationCenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private VaccinationCenterRepository vaccinationCenterRepository;
    public String addDoctor(Doctor doctor) throws EmailIdEmptyException, DoctorAlreadyExistsException {
        if(doctor.getEmailId()==null){
            throw new EmailIdEmptyException("It seems you haven't enter the email, please enter email first!!");
        }
        if(doctorRepository.findByEmailId(doctor.getEmailId())!=null){
            throw new DoctorAlreadyExistsException("Doctor is already exists with the entered email");
        }
        doctorRepository.save(doctor);
        return "Doctor is successfully added";
    }

    public String associateDoctor(AssociateDoctorDTO associateDoctorDTO) throws CenterNotFoundException {
        int doctorId= associateDoctorDTO.getDoctorId();;
        Optional<Doctor> doctorOpt=doctorRepository.findById(doctorId);
        if(doctorOpt.isEmpty()){
            throw new CenterNotFoundException("Doctor is not present with the mentioned id");
        }

        int centerId= associateDoctorDTO.getCenterId();
        Optional<VaccinationCenter> centerOpt=vaccinationCenterRepository.findById(centerId);
        if(centerOpt.isEmpty()){
            throw new CenterNotFoundException("Center is not found with the mentioned id");
        }

        Doctor doctor=doctorOpt.get();
        VaccinationCenter vaccinationCenter=centerOpt.get();
        doctor.setVaccinationCenter(vaccinationCenter);
        vaccinationCenter.getDoctorList().add(doctor);
        vaccinationCenterRepository.save(vaccinationCenter);
        return "Doctor has been associated to vaccination center successfully";
    }
}
