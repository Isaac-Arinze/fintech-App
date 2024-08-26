package com.zikan.fintech_Bank_App.repository;

import com.zikan.fintech_Bank_App.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByEmail (String email);

    Boolean existsByAccountNumber (String accountNumber);

    User findByAccountNumber (String accountNumber);

    Optional <User> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("update User u set u.password = ?2 where u.email = ?1")
    void updatePassword (String email, String password);

}
