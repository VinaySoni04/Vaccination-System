package com.WithDBConnection.VaccineManagementSystem.Services;

import com.WithDBConnection.VaccineManagementSystem.Exceptions.VaccinationCenterNotFoundException;
import com.WithDBConnection.VaccineManagementSystem.Models.VaccinationCenter;
import com.WithDBConnection.VaccineManagementSystem.Repositories.VaccinationCenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VaccinationCenterService {
    @Autowired
    private VaccinationCenterRepository vaccinationCenterRepository;
    public String addVaccinationCenter(VaccinationCenter vaccinationCenter) throws VaccinationCenterNotFoundException {
        if(vaccinationCenter.getAddress()==null){
            throw new VaccinationCenterNotFoundException("Address is empty, Please enter Vaccination Center address");
        }
        vaccinationCenterRepository.save(vaccinationCenter);
        return "Vaccination Center added successfully at location:- "+vaccinationCenter.getAddress();
    }
}
