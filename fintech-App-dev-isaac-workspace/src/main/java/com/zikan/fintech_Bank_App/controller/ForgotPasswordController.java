package com.zikan.fintech_Bank_App.controller;


import com.zikan.fintech_Bank_App.dto.EmailDetails;
import com.zikan.fintech_Bank_App.entity.ForgotPassword;
import com.zikan.fintech_Bank_App.entity.User;
import com.zikan.fintech_Bank_App.repository.ForgotPasswordRepository;
import com.zikan.fintech_Bank_App.repository.UserRepository;
import com.zikan.fintech_Bank_App.service.EmailService;
import com.zikan.fintech_Bank_App.utils.ChangePassword;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

@RestController
@RequestMapping("/forgotPassword")

public class ForgotPasswordController {

  private final UserRepository userRepository;
  private final EmailService emailService;
  private final PasswordEncoder passwordEncoder;

  private final ForgotPasswordRepository forgotPasswordRepository;



    public ForgotPasswordController(UserRepository userRepository, EmailService emailService, PasswordEncoder passwordEncoder, ForgotPasswordRepository forgotPasswordRepository) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
        this.forgotPasswordRepository = forgotPasswordRepository;
    }

    @PostMapping("/verifyEmail/{email}")
    public ResponseEntity<String> verifyEmail (@PathVariable String email){

        User user =  userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("Please provide a valid email address"));

        int otp = otpGenerator();
        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(email)
                .messageBody("Thi is is the for your Forgot Password request: " + otp)
                .subject("OTP for Forgot Password request")
                .build();


        ForgotPassword fp = ForgotPassword.builder()
                .otp(otp)
                .expirationTime(new Date(System.currentTimeMillis() + 70 * 10000))
                .user(user)
                .build();

        emailService.sendEmailAlert(emailDetails);
        forgotPasswordRepository.save(fp);

        return ResponseEntity.ok("Email sent for verification");
    }

    @PostMapping("/verifyOtp/{otp}/{email}")
    public ResponseEntity<String> verifyOtp(@PathVariable Integer otp, @PathVariable String email){

        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("please provide a valid email"));
        ForgotPassword fp = forgotPasswordRepository.findByOtpAndUser(otp, user)
                .orElseThrow(()-> new RuntimeException("Invalid otp for email " + email));

        if (fp.getExpirationTime().before(Date.from(Instant.now()))){
            forgotPasswordRepository.deleteById(fp.getFpid());
            return new ResponseEntity<>("OTP has expired", HttpStatus.EXPECTATION_FAILED);
        }
        return ResponseEntity.ok("OTP verified");
    }
    @PostMapping("/changePassword/{email}")
    public ResponseEntity<String> changePasswordHandler(@RequestBody ChangePassword changePassword,
                                                        @PathVariable String email){

        if (!Objects.equals(changePassword.Password(), changePassword.repeatPassword())){
            return new ResponseEntity<>("Please enter the password again", HttpStatus.EXPECTATION_FAILED);
        }
        String encodedPassword = passwordEncoder.encode(changePassword.Password());
        userRepository.updatePassword(email, encodedPassword);

        return ResponseEntity.ok("Password successfully changed");

    }

    private Integer otpGenerator() {
        Random random = new Random();
        return random.nextInt(100_999, 999_999);
    }


}
