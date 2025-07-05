package com.zikan.fintech_Bank_App.dto;

import jakarta.persistence.NamedAttributeNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class USSDRequest {

    private String sessionId;
    private String userInput;
    private String phoneNumber;
}
