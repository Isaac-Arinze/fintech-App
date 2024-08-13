package com.zikan.fintech_Bank_App.service.impl;

import com.zikan.fintech_Bank_App.dto.*;
import com.zikan.fintech_Bank_App.entity.Otp;
import com.zikan.fintech_Bank_App.repository.OtpRepository;
import com.zikan.fintech_Bank_App.service.EmailService;
import com.zikan.fintech_Bank_App.utils.AccountUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Slf4j
public class OtpService {
    private final OtpRepository otpRepository;
    private final EmailService emailService;

    public BankResponse sendOtp (OtpRequest otpRequest){
        //generate d otp
        //check if user has an existing otp to avoid sending multiple otp cos of our DB

        Otp existingOtp = otpRepository.findByEmail(otpRequest.getEmail());
        if (existingOtp != null){
            otpRepository.delete(existingOtp);
        }
        String otp = AccountUtils.generateOtp();
        log.info("Otp: {}", otp);

        otpRepository.save(Otp.builder()
                        .email(otpRequest.getEmail())
                        .otp(otp)
                        .expiresAt(LocalDateTime.now().plusMinutes(2))

                .build());

        // send d otp,
        emailService.sendEmailAlert(EmailDetails.builder()
                        .subject("DO NOT DISCLOSE!!")
                        .recipient(otpRequest.getEmail())
                        .messageBody("Skytech Bank has ssent you an Otp, this otp expires in 2mins " + otp)

                .build());

        // save d otp

        return BankResponse.builder()
                .responseCode("200")
                .responseMessage("SUCCESS")

                .build();

    }

    //To validete Otp,u need to assert d user sent an OTP request

    public BankResponse validateOtp(OtpValidationRequest otpValidationRequest){
        Otp otp = otpRepository.findByEmail(otpValidationRequest.getEmail());
        log.info("Email: {}", otpValidationRequest.getEmail());

        if (otp == null){
            return BankResponse.builder()
                    .responseCode("400")
                    .responseMessage("You have not sent an Otp yet")
                    .build();
        }

        if (otp.getExpiresAt().isBefore(LocalDateTime.now())){
            return BankResponse.builder()
                    .responseCode("400")
                    .responseMessage("Otp is Expired")
                    .build();

        }

        if (!otp.getOtp().equals(otpValidationRequest.getOtp())){
            return BankResponse.builder()
                    .responseCode("400")
                    .responseMessage("Invalid Otp")
                    .build();


        }

        return BankResponse.builder()
                .responseCode("200")
                .responseMessage("SUCCESS")
                .otpResponse(OtpResponse.builder()
                        .isOtpValid(true)
                        .build())

                .build();


    }


    // Assetd Otp hasnt expired
    // assert d otp is correct

}
