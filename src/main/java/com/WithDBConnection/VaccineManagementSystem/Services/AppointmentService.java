package com.WithDBConnection.VaccineManagementSystem.Services;

import com.WithDBConnection.VaccineManagementSystem.DTOs.AppointmentDTO;
import com.WithDBConnection.VaccineManagementSystem.Exceptions.DoctorNotFoundException;
import com.WithDBConnection.VaccineManagementSystem.Exceptions.UserNotFoundException;
import com.WithDBConnection.VaccineManagementSystem.Models.Appointment;
import com.WithDBConnection.VaccineManagementSystem.Models.Doctor;
import com.WithDBConnection.VaccineManagementSystem.Models.User;
import com.WithDBConnection.VaccineManagementSystem.Repositories.AppointmentRepository;
import com.WithDBConnection.VaccineManagementSystem.Repositories.DoctorRepository;
import com.WithDBConnection.VaccineManagementSystem.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private UserRepository userRepository;

    public String bookAppointment(AppointmentDTO appointmentDTO) throws DoctorNotFoundException, UserNotFoundException {
        Optional<Doctor> doctorOpt=doctorRepository.findById(appointmentDTO.getDoctorId());
        if(doctorOpt.isEmpty()){
            throw new DoctorNotFoundException("Doctor is not present with mentioned doctorId");
        }

        Optional<User> userOpt=userRepository.findById((appointmentDTO.getUserId()));
        if(userOpt.isEmpty()){
            throw new UserNotFoundException("User is not present with mentioned userId");
        }

        Doctor doctor=doctorOpt.get();
        User user=userOpt.get();

        // Setting the attributes
        Appointment appointment=new Appointment();
        appointment.setAppointmentDate(appointmentDTO.getAppointmentDate());
        appointment.setAppointmentTime(appointmentDTO.getAppointmentTime());
        appointment.setDoctor(doctor);
        appointment.setUser(user);

        // Saving all attributes
        appointment=appointmentRepository.save(appointment);
        doctor.getAppointments().add(appointment);
        user.getAppointments().add(appointment);
        doctorRepository.save(doctor);
        userRepository.save(user);
        return "Appointment is booked successfully";
    }
}
