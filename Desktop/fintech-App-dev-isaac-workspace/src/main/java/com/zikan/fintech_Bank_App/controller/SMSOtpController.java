package com.zikan.fintech_Bank_App.controller;

import com.zikan.fintech_Bank_App.dto.OtpSMSResponseDto;
import com.zikan.fintech_Bank_App.dto.OtpSMSValidationRequest;
import com.zikan.fintech_Bank_App.dto.SmsOtpRequest;
import com.zikan.fintech_Bank_App.service.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/otp")
@Slf4j
public class SMSOtpController {
    @Autowired
    private SmsService smsService;

    @GetMapping("/process")
    public String processSMS() {
        return "SMS sent";
    }

    @PostMapping("/send-otp-sms")
    public OtpSMSResponseDto sendSMSOtp(@RequestBody SmsOtpRequest smsOtpRequest) {
        log.info("inside sendSMSOtp :: " + smsOtpRequest.getEmail());
        return smsService.sendSMS(smsOtpRequest);
    }
    @PostMapping("/validate-otp-sms")
    public String validateSMSOtp(@RequestBody OtpSMSValidationRequest otpSMSValidationRequest) {
        log.info("inside validateSMSOtp :: " + otpSMSValidationRequest.getEmail() + " " + otpSMSValidationRequest);
        return smsService.validateSMSOtp(otpSMSValidationRequest);
    }

}



