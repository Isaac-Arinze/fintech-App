package com.zikan.fintech_Bank_App.service;

import com.zikan.fintech_Bank_App.dto.TransactionDto;
import com.zikan.fintech_Bank_App.entity.Transaction;

public interface TransactionService {

    void saveTransaction(TransactionDto transactionDto);
}
