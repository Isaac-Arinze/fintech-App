package com.zikan.fintech_Bank_App.dto;

import lombok.Builder;

@Builder
public record MailBody (String to, String subject, String text) {


}
