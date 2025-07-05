package com.zikan.fintech_Bank_App.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SmsOtpRequest {

    private String email;
    private String phoneNumber;
}
