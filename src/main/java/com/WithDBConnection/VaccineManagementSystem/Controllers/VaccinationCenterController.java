package com.WithDBConnection.VaccineManagementSystem.Controllers;

import com.WithDBConnection.VaccineManagementSystem.Exceptions.CenterNotFoundException;
import com.WithDBConnection.VaccineManagementSystem.Exceptions.DoctorNotFoundException;
import com.WithDBConnection.VaccineManagementSystem.Exceptions.VaccinationCenterNotFoundException;
import com.WithDBConnection.VaccineManagementSystem.Models.VaccinationCenter;
import com.WithDBConnection.VaccineManagementSystem.Services.VaccinationCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vaccinationCenter")
public class VaccinationCenterController {
    @Autowired
    private VaccinationCenterService vaccinationCenterService;

    @PostMapping("/add")
    public ResponseEntity<String> addCenter(@RequestBody VaccinationCenter vaccinationCenter) throws VaccinationCenterNotFoundException {
        try{
            String center=vaccinationCenterService.addVaccinationCenter(vaccinationCenter);
            return new ResponseEntity<>(center, HttpStatus.ACCEPTED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/giveDoctorListToCenter")
    public ResponseEntity<String> giveList(@RequestParam Integer centerId) throws CenterNotFoundException, DoctorNotFoundException {
        try {
            String done = vaccinationCenterService.giveList(centerId);
            return new ResponseEntity<>(done, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
}
