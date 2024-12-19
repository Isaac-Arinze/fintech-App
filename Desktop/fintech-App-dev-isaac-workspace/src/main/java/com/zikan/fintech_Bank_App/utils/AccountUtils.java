package com.zikan.fintech_Bank_App.utils;

import org.springframework.boot.builder.SpringApplicationBuilder;

import java.security.SecureRandom;
import java.time.Year;
import java.util.Base64;
import java.util.Random;

public class AccountUtils {

    public static final String ACCOUNT_EXISTS_CODE = "001";
    public static final String ACCOUNT_EXISTS_MESSAGE = "Account already created with users";
    public static final String ACCOUNT_CREATION_SUCCESS = "002";
    public static final String ACCOUNT_CREATION_MESSAGE = "Account successfully created";
    public static final String ACCOUNT_NOT_EXIST_CODE = "003";
    public static final String ACCOUNT_FOUND_CODE = "004";
    public static final String ACCOUNT_NOT_EXIST_MESSAGE = "User with provided Account details does not Exist";
    public static final String ACCOUNT_FOUND_SUCCESS = "User Account Found";

    public static final String ACCOUNT_CREDIT_SUCCESS = "005";
    public static final String ACCOUNT_CREDITED_SUCCESS_MESSAGE = "User account has been successfully credited";
    public static final String INSUFFICIENT_BALANCE_CODE = "006";
    public static final String INSUFFICIENT_BALANCE_MESSAGE = "Account balance is insufficient to initiate debit transaction";

    public static final String ACCOUNT_DEBITED_SUCCESS = "007";
    public static final String ACCOUNT_DEBITED_MESSAGE = "Account has been successfully debited";
    public static final String TRANSFER_SUCCESSFUL_CODE = "008";
    public static final String TRANSFER_SUCCESSFUL_MESSAGE = "Transfer successful";

    public static final String ACCOUNT_DELETED_SUCCESSFUL_CODE = "009";
    public static final String ACCOUNT_DELETED_USER_MESSAGE = "User deleted successfully";
    public static final String USER_ACCOUNT_UPDATED_SUCCESS_CODE = "210";
    public static final String USER_ACCOUNT_UPDATED_SUCCESS_MESSAGE = "User updated successfully";
    public static final String ACCOUNT_VERIFIED_SUCCESS_CODE = "211";
    public static final String ACCOUNT_VERIFIED_SUCCESS_MESSAGE = "Account has been successfully verified";
    public static final String TOKEN_INVALID_CODE = "301";
    public static final String TOKEN_INVALID_MESSAGE = "Token provided is Invalid, kindly provide correct token";
    public static final String USER_ACCOUNT_LOGOUT_CODE = "406";
    public static final String USER_ACCOUNT_LOGOUT_MESSAGE = "User successfully logged out";


    public static String generateAccountNumber() {
        Year currentYear = Year.now();

        int min = 100000;
        int max = 999999;

        int randNumber = (int) Math.floor(Math.random() * (max - min + 1) + min);

        String year = String.valueOf(currentYear);
        String randomNumber = String.valueOf(randNumber);

        return year + randomNumber;
    }

    public static String generateOtp() {
        StringBuilder otp = new StringBuilder();
        Random random = new Random();
        int count = 0;
        while (count < 4) {
            otp.append(random.nextInt(10));
            ++count;
        }
        return otp.toString();
    }

    public static String generateToken() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }
}
