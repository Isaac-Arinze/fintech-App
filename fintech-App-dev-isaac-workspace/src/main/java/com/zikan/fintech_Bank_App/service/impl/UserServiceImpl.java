package com.zikan.fintech_Bank_App.service.impl;

import com.zikan.fintech_Bank_App.config.JwtTokenProvider;
import com.zikan.fintech_Bank_App.dto.*;
import com.zikan.fintech_Bank_App.entity.Role;
import com.zikan.fintech_Bank_App.entity.User;
import com.zikan.fintech_Bank_App.exception.ForbiddenException;
import com.zikan.fintech_Bank_App.repository.TokenRepository;
import com.zikan.fintech_Bank_App.repository.UserRepository;
import com.zikan.fintech_Bank_App.service.EmailService;
import com.zikan.fintech_Bank_App.service.TransactionService;
import com.zikan.fintech_Bank_App.service.UserService;
import com.zikan.fintech_Bank_App.utils.AccountUtils;
import com.zikan.fintech_Bank_App.utils.Helper;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final EmailService emailService;
    private final UserRepository userRepository;
    private final TransactionService transactionService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final ModelMapper modelMapper;
    private final TokenRepository tokenRepository;
    private final JavaMailSender mailSender;

    public UserServiceImpl(EmailService emailService, UserRepository userRepository, TransactionService transactionService,
                           PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager,
                           JwtTokenProvider jwtTokenProvider, ModelMapper modelMapper, TokenRepository tokenRepository,
                           JavaMailSender mailSender) {
        this.emailService = emailService;
        this.userRepository = userRepository;
        this.transactionService = transactionService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.modelMapper = modelMapper;
        this.tokenRepository = tokenRepository;
        this.mailSender = mailSender;
    }

    @Override
    public BankResponse createAccount(UserRequest userRequest) {
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        User newUser = User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .otherName(userRequest.getOtherName())
                .gender(userRequest.getGender())
                .address(userRequest.getAddress())
                .stateOfOrigin(userRequest.getStateOfOrigin())
                .accountNumber(AccountUtils.generateAccountNumber())
                .accountBalance(BigDecimal.ZERO)
                .email(userRequest.getEmail())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .phoneNumber(userRequest.getPhoneNumber())
                .alternativePhoneNumber(userRequest.getAlternativePhoneNumber())
                .status("ACTIVE")
                .role(Role.ROLE_USER)
                .verificationToken(AccountUtils.generateToken())
                .isVerified(false)
                .build();

        User savedUser = userRepository.save(newUser);

        String verificationLink = "http://localhost:8080/api/user/verify?token=" + newUser.getVerificationToken();
        sendVerificationEmail(savedUser.getEmail(), verificationLink);

        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_CREATION_SUCCESS)
                .responseMessage(AccountUtils.ACCOUNT_CREATION_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountBalance(savedUser.getAccountBalance())
                        .accountNumber(savedUser.getAccountNumber())
                        .accountName(savedUser.getFirstName() + " " + savedUser.getLastName() + " " + savedUser.getOtherName())
                        .build())
                .build();
    }

    private void sendVerificationEmail(String email, String verificationLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Account Verification");
        message.setText("Please verify your account by clicking the following link: " + verificationLink);
        mailSender.send(message);
    }

    @Override
    public BankResponse verifyAccount(String token) {
        Optional<User> newUser = userRepository.findByVerificationToken(token);
        if (newUser.isEmpty()) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.TOKEN_INVALID_CODE)
                    .responseMessage(AccountUtils.TOKEN_INVALID_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        User user = newUser.get();
        user.setVerified(true);
        user.setVerificationToken(null); // Token should be used only once
        userRepository.save(user);

        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_VERIFIED_SUCCESS_CODE)
                .responseMessage(AccountUtils.ACCOUNT_VERIFIED_SUCCESS_MESSAGE)
                .accountInfo(null)
                .build();
    }

    @Override
    public BankResponse logoutUser(Authentication authentication) throws BadRequestException {
        if (Helper.isEmpty(authentication)) {
            throw new ForbiddenException("Authentication is required");
        }

        User user = userRepository.findUserByEmail(authentication.getName());
        if (Helper.isEmpty(user)){
            throw new BadRequestException("invalid user");
        }

        user.setLoggedIn(false);
        user.setLastLoginDate(LocalDateTime.now());
        userRepository.save(user);
             return BankResponse.builder()
                .responseCode(AccountUtils.USER_ACCOUNT_LOGOUT_CODE)
                .responseMessage(AccountUtils.USER_ACCOUNT_LOGOUT_MESSAGE)
                     .accountInfo(null)
                     .build();

    }

    @Override
    public BankResponse login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );
        String jwtToken = jwtTokenProvider.generateToken(authentication);

        EmailDetails loginAlert = EmailDetails.builder()
                .subject("You're logged in!")
                .recipient(loginDto.getEmail())
                .messageBody("You logged into your account. If you did not initiate this request, please contact support.")
                .build();
        emailService.sendEmailAlert(loginAlert);

        return BankResponse.builder()
                .responseCode("Login Success")
                .responseMessage(jwtToken)
                .build();
    }

    @Override
    public BankResponse balanceEnquiry(EnquiryRequest request) {
        boolean isAccountExist = userRepository.existsByAccountNumber(request.getAccountNumber());
        if (!isAccountExist) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        User foundUser = userRepository.findByAccountNumber(request.getAccountNumber());
        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_FOUND_CODE)
                .responseMessage(AccountUtils.ACCOUNT_FOUND_SUCCESS)
                .accountInfo(AccountInfo.builder()
                        .accountBalance(foundUser.getAccountBalance())
                        .accountNumber(foundUser.getAccountNumber())
                        .accountName(foundUser.getFirstName() + " " + foundUser.getLastName() + " " + foundUser.getOtherName())
                        .build())
                .build();
    }

    @Override
    public String nameEnquiry(EnquiryRequest request) {
        boolean isAccountExist = userRepository.existsByAccountNumber(request.getAccountNumber());
        if (!isAccountExist) {
            return AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE;
        }

        User foundUser = userRepository.findByAccountNumber(request.getAccountNumber());
        return foundUser.getFirstName() + " " + foundUser.getLastName() + " " + foundUser.getOtherName();
    }

    @Override
    public BankResponse creditAccount(CreditDebitRequest request) {
        boolean isAccountExist = userRepository.existsByAccountNumber(request.getAccountNumber());
        if (!isAccountExist) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        User user = userRepository.findByAccountNumber(request.getAccountNumber());
        user.setAccountBalance(user.getAccountBalance().add(request.getAmount()));
        userRepository.save(user);

        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_CREDIT_SUCCESS)
                .responseMessage(AccountUtils.ACCOUNT_CREDITED_SUCCESS_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountBalance(user.getAccountBalance())
                        .accountNumber(user.getAccountNumber())
                        .accountName(user.getFirstName() + " " + user.getLastName() + " " + user.getOtherName())
                        .build())
                .build();
    }

    @Override
    public BankResponse debitAccount(CreditDebitRequest request) {
        boolean isAccountExist = userRepository.existsByAccountNumber(request.getAccountNumber());
        if (!isAccountExist) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        User user = userRepository.findByAccountNumber(request.getAccountNumber());
        if (user.getAccountBalance().compareTo(request.getAmount()) < 0) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
                    .responseMessage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        user.setAccountBalance(user.getAccountBalance().subtract(request.getAmount()));
        userRepository.save(user);

        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_DEBITED_SUCCESS)
                .responseMessage(AccountUtils.ACCOUNT_DEBITED_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountBalance(user.getAccountBalance())
                        .accountNumber(user.getAccountNumber())
                        .accountName(user.getFirstName() + " " + user.getLastName() + " " + user.getOtherName())
                        .build())
                .build();
    }

    @Override
    public BankResponse transfer(TransferRequest request) {
        User sender = userRepository.findByAccountNumber(request.getSourceAccountNumber());
        User receiver = userRepository.findByAccountNumber(request.getDestinationAccountNumber());

        if (sender == null || receiver == null) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        if (sender.getAccountBalance().compareTo(request.getAmount()) < 0) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
                    .responseMessage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        sender.setAccountBalance(sender.getAccountBalance().subtract(request.getAmount()));
        receiver.setAccountBalance(receiver.getAccountBalance().add(request.getAmount()));

        userRepository.save(sender);
        userRepository.save(receiver);

        return BankResponse.builder()
                .responseCode(AccountUtils.TRANSFER_SUCCESSFUL_CODE)
                .responseMessage(AccountUtils.TRANSFER_SUCCESSFUL_MESSAGE)
                .accountInfo(null)
                .build();
    }

    @Override
    public BankResponse deleteAccount(Long userId) {
        if (!userRepository.existsById(userId)) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        userRepository.deleteById(userId);
        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_DELETED_SUCCESSFUL_CODE)
                .responseMessage(AccountUtils.ACCOUNT_DELETED_USER_MESSAGE)
                .accountInfo(null)
                .build();
    }

    @Override
    public BankResponse updateAccount(UserRequest userRequest, Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        User user = userOptional.get();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setOtherName(userRequest.getOtherName());
        user.setGender(userRequest.getGender());
        user.setAddress(userRequest.getAddress());
        user.setStateOfOrigin(userRequest.getStateOfOrigin());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setAlternativePhoneNumber(userRequest.getAlternativePhoneNumber());
        userRepository.save(user);

        return BankResponse.builder()
                .responseCode(AccountUtils.USER_ACCOUNT_UPDATED_SUCCESS_CODE)
                .responseMessage(AccountUtils.USER_ACCOUNT_UPDATED_SUCCESS_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountBalance(user.getAccountBalance())
                        .accountNumber(user.getAccountNumber())
                        .accountName(user.getFirstName() + " " + user.getLastName() + " " + user.getOtherName())
                        .build())
                .build();
    }
}
