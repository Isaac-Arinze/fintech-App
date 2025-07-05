package com.zikan.fintech_Bank_App.dto;

import lombok.Data;

@Data
public class PaystackVerificationResponse {
    private boolean status;
    private String message;
    private VerificationData data;

    @Data
    public static class VerificationData {
        private String id;
        private String domain;
        private String amount;
        private String currency;
        private String status;
        private String reference;
        private String receipt_number;
        private String message;
        private String gateway_response;
        private String channel;
        private String ip_address;
        private String fees;
        private String requested_amount;
        private String paid_at;
        private String created_at;
        private String updated_at;
        private Customer customer;
        private Authorization authorization;
    }

    @Data
    public static class Customer {
        private String id;
        private String first_name;
        private String last_name;
        private String email;
        private String customer_code;
        private String phone;
        private String metadata;
        private String risk_action;
    }

    @Data
    public static class Authorization {
        private String authorization_code;
        private String bin;
        private String last4;
        private String exp_month;
        private String exp_year;
        private String channel;
        private String card_type;
        private String bank;
        private String country_code;
        private String brand;
        private String reusable;
        private String signature;
        private String account_name;
    }
} 