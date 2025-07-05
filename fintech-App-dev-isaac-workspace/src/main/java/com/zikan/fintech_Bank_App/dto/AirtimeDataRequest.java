package com.zikan.fintech_Bank_App.dto;

import lombok.Data;

@Data
public class AirtimeDataRequest {
    private String phoneNumber;
    private String amount;
    private String network; // MTN, GLO, AIRTEL, 9MOBILE
    private String type; // airtime, data
    private String dataPlan; // For data purchases
    private String email;
    private String reference;
} 