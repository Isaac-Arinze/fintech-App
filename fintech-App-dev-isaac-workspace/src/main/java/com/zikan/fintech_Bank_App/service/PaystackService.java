package com.zikan.fintech_Bank_App.service;

import com.zikan.fintech_Bank_App.dto.*;

public interface PaystackService {
    
    // Core Paystack operations
    PaystackTransactionResponse initializeTransaction(PaystackTransactionRequest request);
    PaystackVerificationResponse verifyTransaction(String reference);
    
    // Specific payment operations
    PaystackTransactionResponse purchaseAirtime(AirtimeDataRequest request);
    PaystackTransactionResponse purchaseData(AirtimeDataRequest request);
    PaystackTransactionResponse payBill(BillPaymentRequest request);
    
    // Utility methods
    String generateReference();
    String convertToKobo(String amount);
} 