package com.zikan.fintech_Bank_App.dto;

import lombok.Data;

@Data
public class PaystackTransactionResponse {
    private boolean status;
    private String message;
    private TransactionData data;

    @Data
    public static class TransactionData {
        private String authorization_url;
        private String access_code;
        private String reference;
    }
} 