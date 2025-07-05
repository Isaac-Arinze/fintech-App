package com.zikan.fintech_Bank_App.dto;

import lombok.Data;

@Data
public class BillPaymentRequest {
    private String customerId;
    private String amount;
    private String billerCode; // Provider code (e.g., IKEJA_ELECTRIC, EKO_ELECTRIC)
    private String billerName;
    private String accountNumber;
    private String email;
    private String reference;
    private String phoneNumber;
} 