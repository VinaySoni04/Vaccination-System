package com.WithDBConnection.VaccineManagementSystem.Services;

import com.WithDBConnection.VaccineManagementSystem.DTOs.AssociateDoctorDTO;
import com.WithDBConnection.VaccineManagementSystem.DTOs.UpdateDetailsDTO;
import com.WithDBConnection.VaccineManagementSystem.Enum.Gender;
import com.WithDBConnection.VaccineManagementSystem.Exceptions.CenterNotFoundException;
import com.WithDBConnection.VaccineManagementSystem.Exceptions.DoctorAlreadyExistsException;
import com.WithDBConnection.VaccineManagementSystem.Exceptions.DoctorNotFoundException;
import com.WithDBConnection.VaccineManagementSystem.Exceptions.EmailIdEmptyException;
import com.WithDBConnection.VaccineManagementSystem.Models.Doctor;
import com.WithDBConnection.VaccineManagementSystem.Models.VaccinationCenter;
import com.WithDBConnection.VaccineManagementSystem.Repositories.DoctorRepository;
import com.WithDBConnection.VaccineManagementSystem.Repositories.VaccinationCenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
        if(doctorRepository.findByEmailId(doctor.getEmailId()).isPresent()){
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

    public List<String> getAllMaleDocAboveAge40() {
        List<String> allDocs=new ArrayList<>();
        List<Doctor> docs=doctorRepository.findAll();
        Doctor doctor=new Doctor();
        for (Doctor doc:docs){
            if(doc.getAge()>40 && doc.getGender()==Gender.MALE){
                allDocs.add(doc.getName());
            }
        }
        return allDocs;
    }

    public List<String> getAllDocHaveAbove10Appointment() {
        List<String> allDocs=new ArrayList<>();
        List<Doctor> docs=doctorRepository.findAll();
        Doctor doctor=new Doctor();
        for (Doctor doc:docs){
            if(doc.getAppointments().size()>10){
                allDocs.add(doc.getName());
            }
        }
        return allDocs;
    }

    public String getRatio() throws DoctorNotFoundException{
        List<Doctor> docs=doctorRepository.findAll();
        int male=0, female=0;
        for(Doctor doc:docs){
            if(doc.getGender()==Gender.MALE)
                male++;
            if(doc.getGender()==Gender.FEMALE)
                female++;
        }
        if(male==0)
            throw new DoctorNotFoundException("No male doctors are present, Only females doctors are present");
        if(female==0)
            throw new DoctorNotFoundException("No female doctors are present, Only male doctors are present");
        // M:F or in decimals?
        // double ratio=(double)male/female
        return male+":"+female;
    }

    public String updateDetails(UpdateDetailsDTO updateDetailsDTO) throws DoctorNotFoundException{
        String doctorEmailId= updateDetailsDTO.getEmailId();
        Optional<Doctor> doctorOpt=doctorRepository.findByEmailId(doctorEmailId);
        if(doctorOpt.isEmpty()){
            throw new DoctorNotFoundException("Doctor is not present with mentioned email id");
        }
        Doctor doctor=doctorOpt.get();
        doctor.setName(updateDetailsDTO.getNewName());
        doctor.setAge(updateDetailsDTO.getNewAge());
        doctor.setGender(updateDetailsDTO.getNewGender());
        doctorRepository.save(doctor);
        return "All old details are replaced by new details";
    }

    public String giveList(Integer centerId) throws CenterNotFoundException {
        Optional<VaccinationCenter> centerOpt = vaccinationCenterRepository.findById(centerId);
        if (centerOpt.isEmpty()) {
            throw new CenterNotFoundException("Center is not found with given id");
        }
        VaccinationCenter vaccinationCenter = centerOpt.get();
        List<Doctor> doctorList = doctorRepository.findAll();
        for(Doctor doc:doctorList){
            doc.setVaccinationCenter(vaccinationCenter);
        }
        vaccinationCenter.setDoctorList(doctorList);
        vaccinationCenterRepository.save(vaccinationCenter);
        return "List of all doctors is sent to the vaccination center with center id " + centerId;
    }

    public String giveListOfMaleDoctor(Integer centerId) throws CenterNotFoundException{
        Optional<VaccinationCenter> centerOpt = vaccinationCenterRepository.findById(centerId);
        if (centerOpt.isEmpty()) {
            throw new CenterNotFoundException("Center is not found with given id");
        }
        VaccinationCenter vaccinationCenter = centerOpt.get();
        List<Doctor> doctorList = doctorRepository.findAll();
        for(Doctor doc:doctorList){
            if(doc.getGender()==Gender.MALE)
                doc.setVaccinationCenter(vaccinationCenter);
        }
        vaccinationCenter.setDoctorList(doctorList);
        vaccinationCenterRepository.save(vaccinationCenter);
        return "List of all male doctors is sent to the "+vaccinationCenter.getCenterName();
    }

    public String giveListOfMaleDoctorAboveAge40(Integer centerId) throws CenterNotFoundException{
        Optional<VaccinationCenter> centerOpt = vaccinationCenterRepository.findById(centerId);
        if (centerOpt.isEmpty()) {
            throw new CenterNotFoundException("Center is not found with given id");
        }
        VaccinationCenter vaccinationCenter = centerOpt.get();
        List<Doctor> doctorList = doctorRepository.findAll();
        for(Doctor doc:doctorList){
            if(doc.getGender()==Gender.MALE &&  doc.getAge()>40)
                doc.setVaccinationCenter(vaccinationCenter);
        }
        vaccinationCenter.setDoctorList(doctorList);
        vaccinationCenterRepository.save(vaccinationCenter);
        return "List of all male doctors is sent to the "+vaccinationCenter.getCenterName();
    }

    public String giveListOfFemaleDoctor(Integer centerId) throws CenterNotFoundException{
        Optional<VaccinationCenter> centerOpt = vaccinationCenterRepository.findById(centerId);
        if (centerOpt.isEmpty()) {
            throw new CenterNotFoundException("Center is not found with given id");
        }
        VaccinationCenter vaccinationCenter = centerOpt.get();
        List<Doctor> doctorList = doctorRepository.findAll();
        for(Doctor doc:doctorList){
            if(doc.getGender()==Gender.FEMALE)
                doc.setVaccinationCenter(vaccinationCenter);
        }
        vaccinationCenter.setDoctorList(doctorList);
        vaccinationCenterRepository.save(vaccinationCenter);
        return "List of all female doctors is sent to the "+vaccinationCenter.getCenterName();
    }

    public String deleteDoctor(Integer doctorId) throws DoctorNotFoundException{
        Optional<Doctor> doctorOpt=doctorRepository.findById(doctorId);
        if(doctorOpt.isEmpty()){
            throw new DoctorNotFoundException("Doctor is not found with the id "+doctorId);
        }
        Doctor doctor=doctorOpt.get();
        doctorRepository.delete(doctor);
        return "Doctor is removed from database";
    }
}
