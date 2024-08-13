package com.zikan.fintech_Bank_App.controller;


import com.zikan.fintech_Bank_App.dto.*;
import com.zikan.fintech_Bank_App.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User Management APIs")
public class UserController {


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @Operation(
            summary = "Create New User Account",
            description = "Creating a new user and assigning an account ID"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http status 201 CREATED"
    )

   @PostMapping("/create")
    public BankResponse createAccount (@RequestBody UserRequest userRequest){
        return userService.createAccount(userRequest);
    }
    @PostMapping("/login")
    public BankResponse login (@RequestBody LoginDto loginDto){
        return userService.login(loginDto);

    }

    @Operation(
            summary = "Balance Enquiry",
            description = "Check available balance for a specific account number"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http state 200 Success"
    )

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

    @PostMapping("/transfer")
    public BankResponse transfer (@RequestBody TransferRequest request){
       return userService.transfer(request);

    }

}

