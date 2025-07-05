package com.zikan.fintech_Bank_App.repository;

import com.zikan.fintech_Bank_App.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpRepository extends JpaRepository<Otp, Long> {

    Otp findByEmail (String email);


}
