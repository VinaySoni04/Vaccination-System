package com.WithDBConnection.VaccineManagementSystem.Services;

import com.WithDBConnection.VaccineManagementSystem.Exceptions.UserNotFoundException;
import com.WithDBConnection.VaccineManagementSystem.Models.Dose;
import com.WithDBConnection.VaccineManagementSystem.Models.User;
import com.WithDBConnection.VaccineManagementSystem.Repositories.DoseRepository;
import com.WithDBConnection.VaccineManagementSystem.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DoseService {
    @Autowired
    private DoseRepository doseRepository;

    @Autowired
    private UserRepository userRepository;

    public String giveDose(String doseId, Integer userId) throws UserNotFoundException {
        Optional<User> userOpt=userRepository.findById(userId);
        if(userOpt.isEmpty()){
            throw new UserNotFoundException("User is not present with mentioned userId");
        }
        User user=userOpt.get();
        Dose dose=new Dose();

        // Setting attributes
        dose.setDoseId(doseId);
        dose.setUser(user);
        user.setDose(dose);
        userRepository.save(user);
        return "Dose is given successfully to user with userId:- "+user.getUserId();
    }
}
