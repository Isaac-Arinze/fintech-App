package com.zikan.fintech_Bank_App;

public enum EmailTemplate {

    WELCOME_TEMPLATE ("welcome-template"),

    RESET_PASSWORD_TEMPLATE ("password-reset-email-template"),
    EMAIL_VERIFICATION_TEMPLATE ("request-otp-template"),
    INVITATION_MEMBER ("member-acceptance-and-verification-template"),

    INVITATION_TEAM ("team-acceptance-and-verification-template"),

    REQUEST_OTP("request-otp-template");


    private final String description;
    EmailTemplate(String description) {
        this.description = description;
    }
    public String getDescription (){
        return description;
    }
}
