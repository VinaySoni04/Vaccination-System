package com.WithDBConnection.VaccineManagementSystem.Services;

import com.WithDBConnection.VaccineManagementSystem.DTOs.AppointmentDTO;
import com.WithDBConnection.VaccineManagementSystem.Exceptions.AppointmentNotRegisteredException;
import com.WithDBConnection.VaccineManagementSystem.Exceptions.DoctorNotFoundException;
import com.WithDBConnection.VaccineManagementSystem.Exceptions.UserNotFoundException;
import com.WithDBConnection.VaccineManagementSystem.Models.Appointment;
import com.WithDBConnection.VaccineManagementSystem.Models.Doctor;
import com.WithDBConnection.VaccineManagementSystem.Models.User;
import com.WithDBConnection.VaccineManagementSystem.Repositories.AppointmentRepository;
import com.WithDBConnection.VaccineManagementSystem.Repositories.DoctorRepository;
import com.WithDBConnection.VaccineManagementSystem.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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

    @Autowired
    private JavaMailSender emailSender;

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

        // Sending a confirmation mail
        String body="Heyy!! "+user.getName()+",\n"+"You have successfully booked a appointment on "+appointment.getAppointmentDate()+" at "+appointment.getAppointmentTime()+"\n"
                    +"Your appointment is scheduled with "+doctor.getName()+".\n"
                    +"Please reach at "+doctor.getVaccinationCenter().getAddress()+" on time."+"\n"
                    +"Please take precautions and mask is mandatory. \n"
                    +"Thank You";
        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setFrom("vsvaccinations@gmail.com");
        mailMessage.setTo(user.getEmailId());
        mailMessage.setSubject("Appointment is confirmed!!");
        mailMessage.setText(body);
        emailSender.send(mailMessage);
        return "Appointment is booked successfully";
    }

    public String cancelAppointment(Integer id) throws AppointmentNotRegisteredException {
        Optional<Appointment> appointmentOpt=appointmentRepository.findById(id);
        if(appointmentOpt.isEmpty()){
            throw new AppointmentNotRegisteredException("You have entered wrong appointment id");
        }
        Appointment appointment=appointmentOpt.get();
        appointmentRepository.delete(appointment);
        return "Appointment with id "+id+" is cancelled successfully";
    }
}
