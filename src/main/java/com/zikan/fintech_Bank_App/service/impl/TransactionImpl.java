package com.zikan.fintech_Bank_App.service.impl;

import com.zikan.fintech_Bank_App.dto.TransactionDto;
import com.zikan.fintech_Bank_App.entity.Transaction;
import com.zikan.fintech_Bank_App.repository.TransactionRepository;
import com.zikan.fintech_Bank_App.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionImpl implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public void saveTransaction(TransactionDto transactionDto) {
        Transaction transaction = Transaction.builder()
                .transactionType(transactionDto.getTransactionType())
                .accountNumber(transactionDto.getAccountNumber())
                .amount(transactionDto.getAmount())
                .status("SUCCESS")
                .build();
        transactionRepository.save(transaction);
        System.out.println("Transaction saved successfully");



    }
}
