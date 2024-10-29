package com.zikan.fintech_Bank_App.service;

import com.zikan.fintech_Bank_App.dto.*;
import org.apache.coyote.BadRequestException;
import org.springframework.security.core.Authentication;

public interface UserService {

    BankResponse createAccount(UserRequest userRequest);
    //balance equiry give info about the user

    BankResponse balanceEnquiry (EnquiryRequest enquiryRequest);

    String nameEnquiry (EnquiryRequest enquiryRequest);

    BankResponse creditAccount (CreditDebitRequest request);

    BankResponse debitAccount (CreditDebitRequest request);

    BankResponse transfer (TransferRequest request);


    BankResponse login (LoginDto loginDto);

    BankResponse updateAccount(UserRequest userRequest, Long userId);

    BankResponse deleteAccount (Long userId);
    BankResponse verifyAccount (String token);
    BankResponse logoutUser(Authentication authentication) throws BadRequestException;

    BankResponse sendOtp();

    BankResponse validateOtp();
    BankResponse resetPassword();
    BankResponse changePassword();


}
