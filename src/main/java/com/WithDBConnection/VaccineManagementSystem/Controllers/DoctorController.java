package com.WithDBConnection.VaccineManagementSystem.Controllers;

import com.WithDBConnection.VaccineManagementSystem.DTOs.AssociateDoctorDTO;
import com.WithDBConnection.VaccineManagementSystem.DTOs.UpdateDetailsDTO;
import com.WithDBConnection.VaccineManagementSystem.Exceptions.*;
import com.WithDBConnection.VaccineManagementSystem.Models.Doctor;
import com.WithDBConnection.VaccineManagementSystem.Services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;

    @PostMapping("/add")
    public ResponseEntity<String> addDoctor(@RequestBody Doctor doctor) throws EmailIdEmptyException, DoctorAlreadyExistsException {
        try{
            String doc=doctorService.addDoctor(doctor);
            return new ResponseEntity<>(doc, HttpStatus.ACCEPTED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/associateWithCenter")
    public ResponseEntity<String> associateDoctor(@RequestBody AssociateDoctorDTO associateDoctorDTO) throws CenterNotFoundException {
        try{
            String associated = doctorService.associateDoctor(associateDoctorDTO);
            return new ResponseEntity<>(associated,HttpStatus.ACCEPTED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAllMaleDocAboveAge40")
    public ResponseEntity<List<String>> getAllMaleDocAboveAge40(){
        return new ResponseEntity<>(doctorService.getAllMaleDocAboveAge40(),HttpStatus.OK);
    }

    @GetMapping("/getAllDocHaveAbove10Appointment")
    public ResponseEntity<List<String>> getAllDocHaveAbove10Appointment(){
        return new ResponseEntity<>(doctorService.getAllDocHaveAbove10Appointment(),HttpStatus.OK);
    }

    @GetMapping("/getRatio")
    public ResponseEntity<String> getRatio() throws DoctorNotFoundException {
        try{
            return new ResponseEntity<>(doctorService.getRatio(),HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateDetailsByEmailId")
    public ResponseEntity<String> updateDetails(@RequestBody UpdateDetailsDTO updateDetailsDTO) throws DoctorNotFoundException {
        try {
            String done=doctorService.updateDetails(updateDetailsDTO);
            return new ResponseEntity<>(done,HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
