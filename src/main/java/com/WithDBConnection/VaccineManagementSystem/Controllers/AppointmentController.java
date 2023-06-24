package com.WithDBConnection.VaccineManagementSystem.Controllers;

import com.WithDBConnection.VaccineManagementSystem.DTOs.AppointmentDTO;
import com.WithDBConnection.VaccineManagementSystem.Exceptions.AppointmentNotRegisteredException;
import com.WithDBConnection.VaccineManagementSystem.Exceptions.DoctorNotFoundException;
import com.WithDBConnection.VaccineManagementSystem.Exceptions.UserNotFoundException;
import com.WithDBConnection.VaccineManagementSystem.Services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;
    @PostMapping("/book")
    public ResponseEntity<String> bookAppointment(@RequestBody AppointmentDTO appointmentDTO) throws DoctorNotFoundException, UserNotFoundException {
        try{
            String success=appointmentService.bookAppointment(appointmentDTO);
            return new ResponseEntity<>(success, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("cancel/{id}")
    public ResponseEntity<String> cancelAppointment(@PathVariable("id") Integer id) throws AppointmentNotRegisteredException {
        try {
            String done=appointmentService.cancelAppointment(id);
            return new ResponseEntity<>(done,HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
}
