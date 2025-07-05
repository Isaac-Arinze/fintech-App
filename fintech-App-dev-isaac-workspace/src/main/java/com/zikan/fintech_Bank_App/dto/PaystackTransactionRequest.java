package com.zikan.fintech_Bank_App.dto;

import lombok.Data;

@Data
public class PaystackTransactionRequest {
    private String email;
    private String amount; // Amount in kobo (smallest currency unit)
    private String reference;
    private String callback_url;
    private String currency = "NGN";
    private String channels; // card, bank, ussd, qr, mobile_money, bank_transfer
    private String subaccount;
    private String transaction_charge;
    private String bearer;
    private String metadata; // JSON string for additional data
} 