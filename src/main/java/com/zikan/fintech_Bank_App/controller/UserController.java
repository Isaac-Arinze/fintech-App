package com.zikan.fintech_Bank_App.controller;


import com.zikan.fintech_Bank_App.config.JwtTokenProvider;
import com.zikan.fintech_Bank_App.dto.*;
import com.zikan.fintech_Bank_App.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User Management APIs")
public class UserController {



    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    public UserController(UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
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
    public BankResponse createAccount(@RequestBody UserRequest userRequest) {
        return userService.createAccount(userRequest);
    }

    @PostMapping("/login")
    public BankResponse login(@RequestBody LoginDto loginDto) {
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
    public BankResponse balanceEnquiry(@RequestBody EnquiryRequest request) {
        return userService.balanceEnquiry(request);
    }

    @GetMapping("/nameEnquiry")
    public String nameEnquiry(@RequestBody EnquiryRequest request) {
        return userService.nameEnquiry(request);
    }

    @PostMapping("/credit")
    public BankResponse creditAccount(@RequestBody CreditDebitRequest request) {
        return userService.creditAccount(request);
    }

    @PostMapping("/debit")
    public BankResponse debitAccount(@RequestBody CreditDebitRequest request) {
        return userService.debitAccount(request);
    }

    @PostMapping("/transfer")
    public BankResponse transfer(@RequestBody TransferRequest request) {
        return userService.transfer(request);

    }

    @DeleteMapping("/delete/{userId}")
    public BankResponse deleteUser(@PathVariable Long userId) {
        return userService.deleteAccount(userId);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<BankResponse> updateAccount(@RequestBody UserRequest userRequest, @PathVariable Long userId) {
        BankResponse response = userService.updateAccount(userRequest, userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/verify")
    public ResponseEntity<BankResponse> verifyAccount(@RequestParam String token) {
        BankResponse response = userService.verifyAccount(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity <?> logout(@RequestHeader ("Authorization") Authentication authentication) throws BadRequestException {
        BankResponse bankResponse = userService.logoutUser(authentication);
        return new ResponseEntity<>(bankResponse, HttpStatus.OK);
    }
        // Retrieve the JWT token from the request header


}

