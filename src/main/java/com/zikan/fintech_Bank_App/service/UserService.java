package com.zikan.fintech_Bank_App.service;

import com.zikan.fintech_Bank_App.dto.*;

public interface UserService {

    BankResponse createAccount(UserRequest userRequest);
    //balance equiry give info about the user

    BankResponse balanceEnquiry (EnquiryRequest enquiryRequest);

    String nameEnquiry (EnquiryRequest enquiryRequest);

    BankResponse creditAccount (CreditDebitRequest request);

    BankResponse debitAccount (CreditDebitRequest request);

    BankResponse transfer (TransferRequest request);

}
