package com.WithDBConnection.VaccineManagementSystem.Exceptions;

public class DoctorAlreadyExistsException extends Exception{
    public DoctorAlreadyExistsException(String message){
        super(message);
    }
}
