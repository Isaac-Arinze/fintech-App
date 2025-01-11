//package com.zikan.fintech_Bank_App.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import io.github.cdimascio.dotenv.Dotenv;
//
//@Configuration
//public class AppConfig {
//
//    @Bean
//    public Dotenv dotenv() {
//        return Dotenv.load();
//    }
//
//    @Value("${DATABASE_URL}")
//    private String databaseUrl;
//
//    @Value("${DATABASE_USERNAME}")
//    private String databaseUsername;
//
//    @Value("${DATABASE_PASSWORD}")
//    private String databasePassword;
//
//    @Value("${MAIL_USERNAME}")
//    private String mailUsername;
//
//    @Value("${MAIL_PASSWORD}")
//    private String mailPassword;
//
//    @Value("${GITHUB_CLIENT_ID}")
//    private String githubClientId;
//
//    @Value("${GITHUB_CLIENT_SECRET}")
//    private String githubClientSecret;
//
//    @Value("${GOOGLE_CLIENT_ID}")
//    private String googleClientId;
//
//    @Value("${GOOGLE_CLIENT_SECRET}")
//    private String googleClientSecret;
//
//    @Value("${JWT_SECRET}")
//    private String jwtSecret;
//
//    @Value("${TWILIO_ACCOUNT_SID}")
//    private String twilioAccountSid;
//
//    @Value("${TWILIO_AUTH_TOKEN}")
//    private String twilioAuthToken;
//
//    // Getters for the properties
//    public String getDatabaseUrl() {
//        return databaseUrl;
//    }
//
//    public String getDatabaseUsername() {
//        return databaseUsername;
//    }
//
//    public String getDatabasePassword() {
//        return databasePassword;
//    }
//
//    public String getMailUsername() {
//        return mailUsername;
//    }
//
//    public String getMailPassword() {
//        return mailPassword;
//    }
//
//    public String getGithubClientId() {
//        return githubClientId;
//    }
//
//    public String getGithubClientSecret() {
//        return githubClientSecret;
//    }
//
//    public String getGoogleClientId() {
//        return googleClientId;
//    }
//
//    public String getGoogleClientSecret() {
//        return googleClientSecret;
//    }
//
//    public String getJwtSecret() {
//        return jwtSecret;
//    }
//
//    public String getTwilioAccountSid() {
//        return twilioAccountSid;
//    }
//
//    public String getTwilioAuthToken() {
//        return twilioAuthToken;
//    }
//}
