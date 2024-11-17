package com.zikan.fintech_Bank_App;

public interface APIMessage {
    String REQUIRED_FULLNAME = "Name is required";

    String REQUIRED_BVN = "Bvn is required";

    String REQUIRED_USERNAME = "Username is required";

    String REQUIRED_EMAIL = "Email is required";

    String REQUIRED_PHONE = "Phone number is required";

    String REQUIRED_PASSWORD = "Password is required";

    String REQUIRED_USERNAME_OR_EMAIL = "Username OR email is required";

    String ROLE_NOT_FOUND = "Role not found.";

    String ROLE_EXISTS = "Role already exist";

    String INACTIVE_ACCOUNT = "Invalid account";

    String INVALID_ROLE = "Invalid Role";

    String REQUIRE_SUPER_ADMIN_ACCESS = "Requires super admin access";

    String EMAIL_NOT_FOUND = "Email is not found";

    String PASSWORD_NOT_FOUND = "Password is not found";

    String PASSWORD_IS_TOO_SHORT = "Password length is too short";

    String INVALID_EMAIL = "Invalid email address provided";

    String INVALID_EMAIL_OR_PHONE = "Invalid credentials provided";

    String NAME_EMPTY = "Name is required";

    String NAME_IS_TOO_SHORT = "Name is too short";

    String PHONE_NUMBER_EMPTY = "Phone number is required";

    String PHONE_NUMBER_IS_INVALID = "Phone number is invalid";

    String EMAIL_OR_PHONE_IS_REQUIRED = "Email or phonenumber is required";

    String AMOUNT_IS_REQUIRED = "Amount is required";

    String TOKEN_NOT_PROVIDED = "Token is required";

    String INVALID_TOKEN = "token is invalid";

    String SUCCESSFUL_REQUEST = "Your request was successful";

    String USER_REGISTERED_SUCCESSFULLY = "User registered successfully";

    String SERVICE_CREATED_SUCCESSFULLY = "Application service was created successfully";

    String PROVIDER_CREATED_SUCCESSFULLY = "New provider was created successfully";

    String EMAIL_SEND_FROM = "noreply@skybanc.ng";

    String EMAIL_ADDRESS_INFO = "info@skybanc.com.ng";

    String EMAIL_ADDRESS_HELP = "support@clearwage.com.ng";

    String EMAIL_WELCOME_SUBJECT = "Welcome to SkyBanc";

    String EMAIL_WELCOME_MESSAGE = "Skybanc community and I are super excited to welcome you to the platform.";

    String EMAIL_VERIFICATION_SUBJECT = "Skybanc Email verification";

    String ACCOUNT_NUMBER_CREATION_SUBJECT = "SkyBanc Virtual Account";

    String EMAIL_COMPLIANCE = "compliance@skybanc.ng";

    String EMAIL_DEVELOPER = "supporte@skybanc.ng";

    String FORGOT_PASSWORD_CODE = "Forgot password must be supplied";

    String SERVICE_TYPE = "Service type is required.";
    String SERVICE = "Service s required.";
    String INSTITUTION_NAME_REQUIRED = "Institution name is required";
    String CERTIFICATE_OF_INCORPORATION_REQUIRED = "Certificate of Incorporation is required";
    String TITLE_REQUIRED = "Title is required";
    String ADDRESS_REQUIRED = "Address is required";

    String BANK_ACCOUNT_ID_IS_REQUIRED = "Bank account ID is required";


}
