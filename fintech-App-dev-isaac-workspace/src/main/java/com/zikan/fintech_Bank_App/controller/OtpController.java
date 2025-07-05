package com.zikan.fintech_Bank_App.controller;

import com.zikan.fintech_Bank_App.dto.BankResponse;
import com.zikan.fintech_Bank_App.dto.OtpRequest;
import com.zikan.fintech_Bank_App.dto.OtpValidationRequest;
import com.zikan.fintech_Bank_App.service.impl.OtpService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/otp")
@AllArgsConstructor
public class OtpController {
    private final OtpService otpService;

    @PostMapping("/sendOtp")
    public BankResponse sendOtp (@RequestBody OtpRequest otpRequest){
        return otpService.sendOtp(otpRequest);
    }

    @PostMapping("/validateOtp")
    public BankResponse valideOtp (@RequestBody OtpValidationRequest otpValidationRequest){
        return otpService.validateOtp(otpValidationRequest);
    }
}
