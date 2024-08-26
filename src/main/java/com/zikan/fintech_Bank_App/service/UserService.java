package com.zikan.fintech_Bank_App.service;

import com.zikan.fintech_Bank_App.dto.*;
import com.zikan.fintech_Bank_App.entity.User;

public interface UserService {

    BankResponse createAccount(UserRequest userRequest);
    //balance equiry give info about the user

    BankResponse balanceEnquiry (EnquiryRequest enquiryRequest);

    String nameEnquiry (EnquiryRequest enquiryRequest);

    BankResponse creditAccount (CreditDebitRequest request);

    BankResponse debitAccount (CreditDebitRequest request);

    BankResponse transfer (TransferRequest request);

    BankResponse login (LoginDto loginDto);

    BankResponse sendOtp();

    BankResponse validateOtp();
    BankResponse resetPassword();
    BankResponse changePassword();

    User updateAccount(UserRequest userRequest, Long userId);

    void deleteAccount (Long userId);

}
