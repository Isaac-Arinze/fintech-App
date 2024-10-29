package com.zikan.fintech_Bank_App.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BankResponse {

    private String responseCode;
    private String responseMessage;
    private AccountInfo accountInfo; // we are makking use of composition here
    private OtpResponse otpResponse;

}
