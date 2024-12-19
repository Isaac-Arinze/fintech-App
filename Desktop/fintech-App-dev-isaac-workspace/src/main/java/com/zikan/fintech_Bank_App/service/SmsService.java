package com.zikan.fintech_Bank_App.service;

import com.zikan.fintech_Bank_App.dto.OtpSMSResponseDto;
import com.zikan.fintech_Bank_App.dto.OtpSMSValidationRequest;
import com.zikan.fintech_Bank_App.dto.SmsOtpRequest;

public interface SmsService {

    OtpSMSResponseDto sendSMS (SmsOtpRequest smsOtpRequest);

    String validateSMSOtp (OtpSMSValidationRequest otpSMSValidationRequest);
}
