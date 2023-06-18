package com.WithDBConnection.VaccineManagementSystem.Services;

import com.WithDBConnection.VaccineManagementSystem.DTOs.UpdateEmailDTO;
import com.WithDBConnection.VaccineManagementSystem.Enum.Gender;
import com.WithDBConnection.VaccineManagementSystem.Exceptions.CenterNotFoundException;
import com.WithDBConnection.VaccineManagementSystem.Exceptions.EmailIdEmptyException;
import com.WithDBConnection.VaccineManagementSystem.Exceptions.MobileNumberNotFoundException;
import com.WithDBConnection.VaccineManagementSystem.Exceptions.UserNotFoundException;
import com.WithDBConnection.VaccineManagementSystem.Models.Doctor;
import com.WithDBConnection.VaccineManagementSystem.Models.Dose;
import com.WithDBConnection.VaccineManagementSystem.Models.User;
import com.WithDBConnection.VaccineManagementSystem.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public String addUser(User user) throws MobileNumberNotFoundException, EmailIdEmptyException {
        if(user.getEmailId()==null){
            throw new EmailIdEmptyException("It seems you haven't enter the email, please enter email id");
        }
        if(user.getMobileNo()==null){
            throw new MobileNumberNotFoundException("It seems you haven't enter the mobile number, please enter mobile number");
        }
        user=userRepository.save(user);
        return "User is successfully added with required information";
    }

    public String getVaccinationDate(Integer userId) throws UserNotFoundException {
        Optional<User> userOpt=userRepository.findById(userId);
        if(userOpt.isEmpty()){
            throw new UserNotFoundException("Invalid user id");
        }
        User user=userOpt.get();
        Dose dose=user.getDose();
        return ""+dose.getVaccinationDate();
    }

    public String updateEmail(UpdateEmailDTO updateEmailDTO) throws UserNotFoundException{
        int userId=updateEmailDTO.getUserId();
        Optional<User> userOpt=userRepository.findById(userId);
        if(userOpt.isEmpty()){
            throw new UserNotFoundException("User is not present with the mentioned user id");
        }
        User user=userOpt.get();
        user.setEmailId(updateEmailDTO.getNewEmailId());
        userRepository.save(user);
        return "Old email has been replaced by new one "+updateEmailDTO.getNewEmailId();
    }

    public User getUserByEmail(String emailId) throws UserNotFoundException {
        Optional<User> userOpt= Optional.ofNullable(userRepository.findByEmailId(emailId));
        if (userOpt.isEmpty()){
            throw new UserNotFoundException("Invalid email id");
        }
        User user=userOpt.get();
        return user;
    }
}
