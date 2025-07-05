package com.zikan.fintech_Bank_App.service.impl;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.zikan.fintech_Bank_App.config.TwilloConfig;
import com.zikan.fintech_Bank_App.dto.OtpSMSResponseDto;
import com.zikan.fintech_Bank_App.dto.OtpSMSValidationRequest;
import com.zikan.fintech_Bank_App.dto.SmsOtpRequest;
import com.zikan.fintech_Bank_App.entity.OtpSmsStatus;
import com.zikan.fintech_Bank_App.service.SmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class SmsServiceImpl implements SmsService {

    private static final Logger logger = LoggerFactory.getLogger(SmsServiceImpl.class);

    @Autowired
    private TwilloConfig twilloConfig;

    private final Map<String, String> otpMap = new HashMap<>();

    @Override
    public OtpSMSResponseDto sendSMS(SmsOtpRequest smsOtpRequest) {
        OtpSMSResponseDto otpSMSResponseDto;
        try {
            PhoneNumber to = new PhoneNumber(smsOtpRequest.getPhoneNumber());
            PhoneNumber from = new PhoneNumber(twilloConfig.getTwilioPhoneNumber());
            String smsOtp = generateSMSOTP();
            String otpSmsMessage = "Dear Customer, Your OTP is " + smsOtp + " for testing sending SMS through Spring Boot application. Thank you.";

            Message message = Message.creator(to, from, otpSmsMessage).create();

            otpMap.put(smsOtpRequest.getEmail(), smsOtp);
            otpSMSResponseDto = new OtpSMSResponseDto(OtpSmsStatus.DELIVERED, otpSmsMessage);
        } catch (Exception e) {
            logger.error("Failed to send SMS OTP. PhoneNumber: {}, Error: {}", smsOtpRequest.getPhoneNumber(), e.getMessage(), e);
            otpSMSResponseDto = new OtpSMSResponseDto(OtpSmsStatus.FAILED, e.getMessage());
        }
        return otpSMSResponseDto;
    }

    @Override
    public String validateSMSOtp(OtpSMSValidationRequest otpSMSValidationRequest) {
        String email = otpSMSValidationRequest.getEmail();
        String storedOtp = otpMap.get(email);

        if (storedOtp != null && storedOtp.equals(otpSMSValidationRequest.getOtpNumber())) {
            otpMap.remove(email);  // Optionally, remove the OTP after successful validation
            return "OTP is valid";
        } else {
            return "OTP is invalid";
        }
    }

    private String generateSMSOTP() {
        return new DecimalFormat("000000").format(new Random().nextInt(1000000));
    }
}
