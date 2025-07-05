package com.zikan.fintech_Bank_App.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class USSDResponse {
    private String sessionId;
    private String message;
    private boolean isEndSession;
}
