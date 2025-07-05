package com.zikan.fintech_Bank_App.controller;

import com.itextpdf.text.DocumentException;
import com.zikan.fintech_Bank_App.entity.Transaction;
import com.zikan.fintech_Bank_App.service.impl.BankStatement;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/bankstatement")
public class TransactionController {

    private BankStatement bankStatement;


    @GetMapping()
    public List<Transaction> generateBankStatement (@RequestParam String accountNumber,
                                                    @RequestParam String startDate,
                                                    @RequestParam String endDate) throws DocumentException, IOException {

        return bankStatement.generateStatement(accountNumber, startDate, endDate);


    }

}
