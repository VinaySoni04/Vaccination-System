package com.WithDBConnection.VaccineManagementSystem.Repositories;

import com.WithDBConnection.VaccineManagementSystem.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    // Just define the function, remaining things will take cared by JPA repository
    Optional<User> findByEmailId(String emailId);
}
