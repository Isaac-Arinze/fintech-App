package com.zikan.fintech_Bank_App.controller;

import com.zikan.fintech_Bank_App.dto.*;
import com.zikan.fintech_Bank_App.service.PaystackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Payment Gateway", description = "Paystack payment gateway integration")
public class PaymentController {

    private final PaystackService paystackService;

    @PostMapping("/airtime")
    @Operation(summary = "Purchase airtime", description = "Initialize airtime purchase transaction")
    public ResponseEntity<Map<String, Object>> purchaseAirtime(@RequestBody AirtimeDataRequest request) {
        try {
            log.info("Received airtime purchase request for phone: {}", request.getPhoneNumber());
            
            // Generate reference if not provided
            if (request.getReference() == null || request.getReference().isEmpty()) {
                request.setReference(paystackService.generateReference());
            }
            
            PaystackTransactionResponse response = paystackService.purchaseAirtime(request);
            
            Map<String, Object> result = new HashMap<>();
            result.put("status", response.isStatus());
            result.put("message", response.getMessage());
            result.put("authorization_url", response.getData().getAuthorization_url());
            result.put("reference", response.getData().getReference());
            result.put("access_code", response.getData().getAccess_code());
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("Error processing airtime purchase: {}", e.getMessage(), e);
            Map<String, Object> error = new HashMap<>();
            error.put("status", false);
            error.put("message", "Failed to process airtime purchase: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PostMapping("/data")
    @Operation(summary = "Purchase data", description = "Initialize data purchase transaction")
    public ResponseEntity<Map<String, Object>> purchaseData(@RequestBody AirtimeDataRequest request) {
        try {
            log.info("Received data purchase request for phone: {}", request.getPhoneNumber());
            
            // Generate reference if not provided
            if (request.getReference() == null || request.getReference().isEmpty()) {
                request.setReference(paystackService.generateReference());
            }
            
            PaystackTransactionResponse response = paystackService.purchaseData(request);
            
            Map<String, Object> result = new HashMap<>();
            result.put("status", response.isStatus());
            result.put("message", response.getMessage());
            result.put("authorization_url", response.getData().getAuthorization_url());
            result.put("reference", response.getData().getReference());
            result.put("access_code", response.getData().getAccess_code());
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("Error processing data purchase: {}", e.getMessage(), e);
            Map<String, Object> error = new HashMap<>();
            error.put("status", false);
            error.put("message", "Failed to process data purchase: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PostMapping("/bill")
    @Operation(summary = "Pay bill", description = "Initialize bill payment transaction")
    public ResponseEntity<Map<String, Object>> payBill(@RequestBody BillPaymentRequest request) {
        try {
            log.info("Received bill payment request for account: {}", request.getAccountNumber());
            
            // Generate reference if not provided
            if (request.getReference() == null || request.getReference().isEmpty()) {
                request.setReference(paystackService.generateReference());
            }
            
            PaystackTransactionResponse response = paystackService.payBill(request);
            
            Map<String, Object> result = new HashMap<>();
            result.put("status", response.isStatus());
            result.put("message", response.getMessage());
            result.put("authorization_url", response.getData().getAuthorization_url());
            result.put("reference", response.getData().getReference());
            result.put("access_code", response.getData().getAccess_code());
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("Error processing bill payment: {}", e.getMessage(), e);
            Map<String, Object> error = new HashMap<>();
            error.put("status", false);
            error.put("message", "Failed to process bill payment: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/verify/{reference}")
    @Operation(summary = "Verify transaction", description = "Verify a payment transaction by reference")
    public ResponseEntity<Map<String, Object>> verifyTransaction(@PathVariable String reference) {
        try {
            log.info("Verifying transaction with reference: {}", reference);
            
            PaystackVerificationResponse response = paystackService.verifyTransaction(reference);
            
            Map<String, Object> result = new HashMap<>();
            result.put("status", response.isStatus());
            result.put("message", response.getMessage());
            
            if (response.getData() != null) {
                result.put("transaction_id", response.getData().getId());
                result.put("amount", response.getData().getAmount());
                result.put("currency", response.getData().getCurrency());
                result.put("status", response.getData().getStatus());
                result.put("gateway_response", response.getData().getGateway_response());
                result.put("paid_at", response.getData().getPaid_at());
                result.put("channel", response.getData().getChannel());
            }
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("Error verifying transaction: {}", e.getMessage(), e);
            Map<String, Object> error = new HashMap<>();
            error.put("status", false);
            error.put("message", "Failed to verify transaction: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PostMapping("/callback")
    @Operation(summary = "Payment callback", description = "Handle Paystack webhook callback")
    public ResponseEntity<String> handleCallback(@RequestBody Map<String, Object> callbackData) {
        try {
            log.info("Received payment callback: {}", callbackData);
            
            // Process the callback data
            // You can add your business logic here
            // For example, update transaction status in your database
            
            return ResponseEntity.ok("OK");
        } catch (Exception e) {
            log.error("Error processing callback: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body("Error processing callback");
        }
    }

    @GetMapping("/reference/generate")
    @Operation(summary = "Generate reference", description = "Generate a unique transaction reference")
    public ResponseEntity<Map<String, String>> generateReference() {
        String reference = paystackService.generateReference();
        Map<String, String> result = new HashMap<>();
        result.put("reference", reference);
        return ResponseEntity.ok(result);
    }
} 