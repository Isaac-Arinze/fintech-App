package com.zikan.fintech_Bank_App.controller;


import com.zikan.fintech_Bank_App.dto.BankResponse;
import com.zikan.fintech_Bank_App.dto.CreditDebitRequest;
import com.zikan.fintech_Bank_App.dto.EnquiryRequest;
import com.zikan.fintech_Bank_App.dto.UserRequest;
import com.zikan.fintech_Bank_App.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


   @PostMapping("/create")
    public BankResponse createAccount (@RequestBody UserRequest userRequest){
        return userService.createAccount(userRequest);
    }

    @GetMapping("/balanceEnqiry")
    public BankResponse balanceEnquiry (@RequestBody EnquiryRequest request){
        return userService.balanceEnquiry(request);
    }

    @GetMapping("/nameEnquiry")
    public String nameEnquiry (@RequestBody EnquiryRequest request){
        return userService.nameEnquiry(request);
    }

    @PostMapping("/credit")
    public BankResponse creditAccount (@RequestBody CreditDebitRequest request){
        return userService.creditAccount(request);
    }

    @PostMapping("/debit")
    public BankResponse debitAccount (@RequestBody CreditDebitRequest request){
        return userService.debitAccount(request);
    }

}

