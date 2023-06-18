package com.WithDBConnection.VaccineManagementSystem.Controllers;

import com.WithDBConnection.VaccineManagementSystem.DTOs.UpdateEmailDTO;
import com.WithDBConnection.VaccineManagementSystem.Exceptions.EmailIdEmptyException;
import com.WithDBConnection.VaccineManagementSystem.Exceptions.MobileNumberNotFoundException;
import com.WithDBConnection.VaccineManagementSystem.Exceptions.UserNotFoundException;
import com.WithDBConnection.VaccineManagementSystem.Models.User;
import com.WithDBConnection.VaccineManagementSystem.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody User user) throws MobileNumberNotFoundException, EmailIdEmptyException {
        try{
            String added = userService.addUser(user);
            return new ResponseEntity<>(added, HttpStatus.ACCEPTED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getVaccinationDate")
    public ResponseEntity<String> getVaccinationDate(@RequestParam("userId") Integer userId) throws UserNotFoundException {
        try {
            String done = userService.getVaccinationDate(userId);
            return new ResponseEntity<>(done, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateEmail")
    public ResponseEntity<String> updateEmail(@RequestBody UpdateEmailDTO updateEmailDTO) throws UserNotFoundException {
        try {
            String done = userService.updateEmail(updateEmailDTO);
            return new ResponseEntity<>(done, HttpStatus.ACCEPTED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getByEmail/{emailId}")
    public ResponseEntity<User> getUserByEmailId(@PathVariable("emailId") String emailId) throws UserNotFoundException{
        try {
            User user=userService.getUserByEmail(emailId);
            return new ResponseEntity<>(user,HttpStatus.OK);
        } catch (Exception e){
            String errorMessage = e.getMessage();
            User errorUser = parseUserFromString(errorMessage);
            return new ResponseEntity<>(errorUser, HttpStatus.NOT_FOUND);
        }
    }
}
