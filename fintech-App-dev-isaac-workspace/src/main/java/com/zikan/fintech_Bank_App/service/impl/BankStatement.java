package com.zikan.fintech_Bank_App.service.impl;


import com.itextpdf.text.*;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.zikan.fintech_Bank_App.dto.EmailDetails;
import com.zikan.fintech_Bank_App.entity.Transaction;
import com.zikan.fintech_Bank_App.entity.User;
import com.zikan.fintech_Bank_App.repository.TransactionRepository;
import com.zikan.fintech_Bank_App.repository.UserRepository;
import com.zikan.fintech_Bank_App.service.EmailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class BankStatement {

    //Retrieve list of transaction within a date range given an account number
    private TransactionRepository transactionRepository;

    private EmailService emailService;

    private UserRepository userRepository;
    //location where file is to be saved
    private static final String FILE = System.getProperty("user.home") + "/Downloads/MyStatement.pdf";

    public List<Transaction> generateStatement (String accountNumber, String startDate, String endDate) throws FileNotFoundException, DocumentException {
        //cnvert string to Local date

        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);
        LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE);


        List<Transaction> transactionList = transactionRepository.findAll().stream()
                .filter(transaction -> transaction.getAccountNumber().equals(accountNumber))
                .filter(transaction -> transaction.getCreatedAt() != null) // Check for null first
                .filter(transaction -> !transaction.getCreatedAt().isBefore(start) && !transaction.getCreatedAt().isAfter(end)) // Check if within the range
                .toList();

        User user = userRepository.findByAccountNumber(accountNumber);
        String customerName = user.getFirstName() + " " + user.getLastName() + " " + user.getOtherName();

        //generate pdf file of transactions

        Rectangle bankStatementSize = new Rectangle(PageSize.A4);

        Document document = new Document(bankStatementSize);
        log.info("Setting size of document");

        OutputStream outputStream = new FileOutputStream(FILE);
        PdfWriter.getInstance(document, outputStream);
        document.open();

        PdfPTable bankInfoTable = new PdfPTable(1);
        PdfPCell bankName = new PdfPCell(new Phrase("Skytech Technology Limited"));
        bankName.setBorder(0);
        bankName.setBackgroundColor(BaseColor.MAGENTA);
        bankName.setPadding(20f);

        PdfPCell bankAddress = new PdfPCell(new Phrase("km 14, Ikota Lekki Expressway Ajah, Lagos Nigeria"));
        bankAddress.setBorder(0);
        bankInfoTable.addCell(bankName);
        bankInfoTable.addCell(bankAddress);

        PdfPTable statementInfo = new PdfPTable(2);
        PdfPCell customerInfo = new PdfPCell(new Phrase("start Date: " + startDate));
        customerInfo.setBorder(0);
        PdfPCell statement = new PdfPCell(new Phrase("STATEMENT OF ACCOUNT"));
        statement.setBorder(0);
        PdfPCell stopDate = new PdfPCell(new Phrase("End Date: " + endDate));
        stopDate.setBorder(0);

        PdfPCell name = new PdfPCell(new Phrase("Customer name: " + customerName));
        name.setBorder(0);
        PdfPCell addSpace = new PdfPCell();
        addSpace.setBorder(0);

        PdfPCell address = new PdfPCell(new Phrase("Customer Address: " + user.getAddress()));
        address.setBorder(0);

        PdfPTable transactionsTable = new PdfPTable(4);
        PdfPCell date = new PdfPCell();
        date.setBackgroundColor(BaseColor.PINK);
        date.setBorder(0);

        PdfPCell  transactionType = new PdfPCell(new Phrase("TRANSACTION TYPE"));
        transactionType.setBackgroundColor(BaseColor.PINK);
        transactionType.setBorder(0);

        PdfPCell transactionAmount = new PdfPCell(new Phrase("TRANSACTION AMOUNT"));
        transactionAmount.setBackgroundColor(BaseColor.PINK);
        transactionAmount.setBorder(0);

        PdfPCell status = new PdfPCell(new Phrase(""));
        status.setBackgroundColor(BaseColor.PINK);
        status.setBorder(0);

        transactionsTable.addCell(date);
        transactionsTable.addCell(transactionType);
        transactionsTable.addCell(transactionAmount);
        transactionsTable.addCell(status);

        transactionList.forEach(transaction -> {
            transactionsTable.addCell(new Phrase(transaction.getCreatedAt().toString()));
            transactionsTable.addCell(new Phrase(transaction.getTransactionType()));
            transactionsTable.addCell(new Phrase(transaction.getAmount().toString()));
            transactionsTable.addCell(new Phrase(transaction.getStatus()));

        });

        statementInfo.addCell(customerInfo);
        statementInfo.addCell(statement);
        statementInfo.addCell(endDate);
        statementInfo.addCell(name);
        statementInfo.addCell(addSpace);
        statementInfo.addCell(address);

        document.add(bankInfoTable);
        document.add(statementInfo);
        document.add(transactionsTable);
        document.close();

        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(user.getEmail())
                .subject("STATEMENT OF ACCOUNT")
                .messageBody("Kindly find your requested account statement attached")
                .attachment(FILE)
                .build();

        emailService.sendEmailWithAttachment(emailDetails);

        return transactionList;
    }

    // send d file via email history
}
