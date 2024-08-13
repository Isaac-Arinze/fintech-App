package com.zikan.fintech_Bank_App.repository;

import com.zikan.fintech_Bank_App.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByEmail (String email);

    Boolean existsByAccountNumber (String accountNumber);

    User findByAccountNumber (String accountNumber);

    Optional <User> findByEmail(String email);
}
