package com.zikan.fintech_Bank_App.service;

import com.zikan.fintech_Bank_App.dto.EmailDetails;

public interface EmailService {
    void sendEmailAlert (EmailDetails emailDetails);

}
