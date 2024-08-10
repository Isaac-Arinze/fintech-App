package com.zikan.fintech_Bank_App.repository;

import com.zikan.fintech_Bank_App.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByEmail (String email);

    Boolean existsByAccountNumber (String accountNumber);

    User findByAccountNumber (String accountNumber);
}
