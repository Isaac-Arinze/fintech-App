package com.zikan.fintech_Bank_App.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaystackConfig {

    @Value("${paystack.secret.key}")
    private String secretKey;

    @Value("${paystack.public.key}")
    private String publicKey;

    @Value("${paystack.base.url:https://api.paystack.co}")
    private String baseUrl;

    public String getSecretKey() {
        return secretKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public String getBaseUrl() {
        return baseUrl;
    }
} 