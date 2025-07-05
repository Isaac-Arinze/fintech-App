package com.zikan.fintech_Bank_App.dto;

import com.zikan.fintech_Bank_App.entity.OtpSmsStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OtpSMSResponseDto {
    private OtpSmsStatus status;
    private String message;
}
