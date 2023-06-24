package com.WithDBConnection.VaccineManagementSystem.Services;

import com.WithDBConnection.VaccineManagementSystem.Exceptions.CenterNotFoundException;
import com.WithDBConnection.VaccineManagementSystem.Exceptions.VaccinationCenterNotFoundException;
import com.WithDBConnection.VaccineManagementSystem.Models.Doctor;
import com.WithDBConnection.VaccineManagementSystem.Models.VaccinationCenter;
import com.WithDBConnection.VaccineManagementSystem.Repositories.DoctorRepository;
import com.WithDBConnection.VaccineManagementSystem.Repositories.VaccinationCenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class VaccinationCenterService {
    @Autowired
    private VaccinationCenterRepository vaccinationCenterRepository;

    @Autowired
    private DoctorRepository doctorRepository;
    public String addVaccinationCenter(VaccinationCenter vaccinationCenter) throws VaccinationCenterNotFoundException {
        if(vaccinationCenter.getAddress()==null){
            throw new VaccinationCenterNotFoundException("Address is empty, Please enter Vaccination Center address");
        }
        vaccinationCenterRepository.save(vaccinationCenter);
        return "Vaccination Center added successfully at location:- "+vaccinationCenter.getAddress();
    }

    public String giveList(Integer centerId) throws CenterNotFoundException {
        Optional<VaccinationCenter> centerOpt = vaccinationCenterRepository.findById(centerId);
        if (centerOpt.isEmpty()) {
            throw new CenterNotFoundException("Center is not found with given id");
        }
        VaccinationCenter vaccinationCenter = centerOpt.get();
        Doctor doctor=new Doctor();
        List<Doctor> doctorList = doctorRepository.findAll();
        for(Doctor doc:doctorList){
            doc.setVaccinationCenter(vaccinationCenter);
        }
        vaccinationCenter.setDoctorList(doctorList);
        vaccinationCenterRepository.save(vaccinationCenter);
        return "List of all doctors is sent to the vaccination center with center id " + centerId;
    }
}
