package com.zikan.fintech_Bank_App.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zikan.fintech_Bank_App.config.PaystackConfig;
import com.zikan.fintech_Bank_App.dto.*;
import com.zikan.fintech_Bank_App.service.PaystackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaystackServiceImpl implements PaystackService {

    private final PaystackConfig paystackConfig;
    private final WebClient.Builder webClientBuilder;
    private final ObjectMapper objectMapper;

    private WebClient getWebClient() {
        return webClientBuilder
                .baseUrl(paystackConfig.getBaseUrl())
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + paystackConfig.getSecretKey())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Override
    public PaystackTransactionResponse initializeTransaction(PaystackTransactionRequest request) {
        try {
            log.info("Initializing Paystack transaction for email: {}", request.getEmail());
            
            return getWebClient()
                    .post()
                    .uri("/transaction/initialize")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(PaystackTransactionResponse.class)
                    .block();
        } catch (Exception e) {
            log.error("Error initializing Paystack transaction: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to initialize transaction", e);
        }
    }

    @Override
    public PaystackVerificationResponse verifyTransaction(String reference) {
        try {
            log.info("Verifying Paystack transaction with reference: {}", reference);
            
            return getWebClient()
                    .get()
                    .uri("/transaction/verify/" + reference)
                    .retrieve()
                    .bodyToMono(PaystackVerificationResponse.class)
                    .block();
        } catch (Exception e) {
            log.error("Error verifying Paystack transaction: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to verify transaction", e);
        }
    }

    @Override
    public PaystackTransactionResponse purchaseAirtime(AirtimeDataRequest request) {
        try {
            log.info("Processing airtime purchase for phone: {}", request.getPhoneNumber());
            
            // Create metadata for airtime purchase
            Map<String, Object> metadata = new HashMap<>();
            metadata.put("type", "airtime");
            metadata.put("phone_number", request.getPhoneNumber());
            metadata.put("network", request.getNetwork());
            
            PaystackTransactionRequest paystackRequest = new PaystackTransactionRequest();
            paystackRequest.setEmail(request.getEmail());
            paystackRequest.setAmount(convertToKobo(request.getAmount()));
            paystackRequest.setReference(request.getReference());
            paystackRequest.setCallback_url("http://localhost:8081/api/payment/callback");
            paystackRequest.setCurrency("NGN");
            paystackRequest.setChannels("card,bank,ussd,qr,mobile_money,bank_transfer");
            
            try {
                paystackRequest.setMetadata(objectMapper.writeValueAsString(metadata));
            } catch (Exception e) {
                log.warn("Could not serialize metadata: {}", e.getMessage());
            }
            
            return initializeTransaction(paystackRequest);
        } catch (Exception e) {
            log.error("Error processing airtime purchase: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to process airtime purchase", e);
        }
    }

    @Override
    public PaystackTransactionResponse purchaseData(AirtimeDataRequest request) {
        try {
            log.info("Processing data purchase for phone: {}", request.getPhoneNumber());
            
            // Create metadata for data purchase
            Map<String, Object> metadata = new HashMap<>();
            metadata.put("type", "data");
            metadata.put("phone_number", request.getPhoneNumber());
            metadata.put("network", request.getNetwork());
            metadata.put("data_plan", request.getDataPlan());
            
            PaystackTransactionRequest paystackRequest = new PaystackTransactionRequest();
            paystackRequest.setEmail(request.getEmail());
            paystackRequest.setAmount(convertToKobo(request.getAmount()));
            paystackRequest.setReference(request.getReference());
            paystackRequest.setCallback_url("http://localhost:8081/api/payment/callback");
            paystackRequest.setCurrency("NGN");
            paystackRequest.setChannels("card,bank,ussd,qr,mobile_money,bank_transfer");
            
            try {
                paystackRequest.setMetadata(objectMapper.writeValueAsString(metadata));
            } catch (Exception e) {
                log.warn("Could not serialize metadata: {}", e.getMessage());
            }
            
            return initializeTransaction(paystackRequest);
        } catch (Exception e) {
            log.error("Error processing data purchase: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to process data purchase", e);
        }
    }

    @Override
    public PaystackTransactionResponse payBill(BillPaymentRequest request) {
        try {
            log.info("Processing bill payment for account: {}", request.getAccountNumber());
            
            // Create metadata for bill payment
            Map<String, Object> metadata = new HashMap<>();
            metadata.put("type", "bill_payment");
            metadata.put("biller_code", request.getBillerCode());
            metadata.put("biller_name", request.getBillerName());
            metadata.put("account_number", request.getAccountNumber());
            metadata.put("customer_id", request.getCustomerId());
            
            PaystackTransactionRequest paystackRequest = new PaystackTransactionRequest();
            paystackRequest.setEmail(request.getEmail());
            paystackRequest.setAmount(convertToKobo(request.getAmount()));
            paystackRequest.setReference(request.getReference());
            paystackRequest.setCallback_url("http://localhost:8081/api/payment/callback");
            paystackRequest.setCurrency("NGN");
            paystackRequest.setChannels("card,bank,ussd,qr,mobile_money,bank_transfer");
            
            try {
                paystackRequest.setMetadata(objectMapper.writeValueAsString(metadata));
            } catch (Exception e) {
                log.warn("Could not serialize metadata: {}", e.getMessage());
            }
            
            return initializeTransaction(paystackRequest);
        } catch (Exception e) {
            log.error("Error processing bill payment: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to process bill payment", e);
        }
    }

    @Override
    public String generateReference() {
        return "REF_" + UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase();
    }

    @Override
    public String convertToKobo(String amount) {
        try {
            // Convert Naira to Kobo (1 Naira = 100 Kobo)
            double nairaAmount = Double.parseDouble(amount);
            long koboAmount = (long) (nairaAmount * 100);
            return String.valueOf(koboAmount);
        } catch (NumberFormatException e) {
            log.error("Invalid amount format: {}", amount);
            throw new IllegalArgumentException("Invalid amount format");
        }
    }
} 