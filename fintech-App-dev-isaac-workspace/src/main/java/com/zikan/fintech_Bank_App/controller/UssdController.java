package com.zikan.fintech_Bank_App.controller;

import com.zikan.fintech_Bank_App.dto.USSDRequest;
import com.zikan.fintech_Bank_App.dto.USSDResponse;
import com.zikan.fintech_Bank_App.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping ("/api/v1/ussd")
public class UssdController {

    @Autowired
    private UserService userService;

    @PostMapping("/process")
    public  USSDResponse processUssdRequest(@RequestBody USSDRequest request) {
        String userInput = request.getUserInput();
        String sessionId = request.getSessionId();
        String phoneNumber = request.getPhoneNumber();

        // Handle USSD menu logic
        if (userInput.equals("1")) {
            // Option 1: Check balance
            BigDecimal balance = userService.getBalance(phoneNumber);
            return new USSDResponse(sessionId, "Your balance is: " + balance, true);
        } else if (userInput.equals("2")) {
            // Option 2: Transaction menu
            return new  USSDResponse(sessionId, "Enter recipient account number:", false);
        } else {
            return new  USSDResponse(sessionId, "Invalid option. Please try again.", false);
        }
    }
}



