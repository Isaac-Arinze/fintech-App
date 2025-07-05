package com.zikan.fintech_Bank_App.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OtpValidationRequest {
    private String otp;
    private String email;

}
