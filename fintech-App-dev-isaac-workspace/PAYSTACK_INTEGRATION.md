# Paystack Integration Guide

This document provides instructions for setting up and using the Paystack payment gateway integration in your Spring Boot application.

## Setup Instructions

### 1. Get Your Paystack API Keys

1. Sign up at [Paystack](https://paystack.com/)
2. Go to your dashboard
3. Navigate to Settings > API Keys & Webhooks
4. Copy your **Test Secret Key** and **Test Public Key**

### 2. Update Configuration

Replace the placeholder keys in `src/main/resources/application-dev.yml`:

```yaml
paystack:
  secret:
    key: sk_test_YOUR_ACTUAL_SECRET_KEY_HERE
  public:
    key: pk_test_YOUR_ACTUAL_PUBLIC_KEY_HERE
  base:
    url: https://api.paystack.co
```

### 3. Restart Your Application

```bash
mvnw.cmd clean
mvnw.cmd spring-boot:run
```

## API Endpoints

### 1. Purchase Airtime

**POST** `/api/payment/airtime`

```json
{
  "phoneNumber": "08012345678",
  "amount": "1000",
  "network": "MTN",
  "type": "airtime",
  "email": "user@example.com"
}
```

**Response:**
```json
{
  "status": true,
  "message": "Authorization URL created",
  "authorization_url": "https://checkout.paystack.com/0x234567890",
  "reference": "REF_ABC123DEF456",
  "access_code": "0x234567890"
}
```

### 2. Purchase Data

**POST** `/api/payment/data`

```json
{
  "phoneNumber": "08012345678",
  "amount": "1500",
  "network": "MTN",
  "type": "data",
  "dataPlan": "1.5GB",
  "email": "user@example.com"
}
```

### 3. Pay Bill

**POST** `/api/payment/bill`

```json
{
  "customerId": "CUST123",
  "amount": "5000",
  "billerCode": "IKEJA_ELECTRIC",
  "billerName": "Ikeja Electric",
  "accountNumber": "1234567890",
  "email": "user@example.com",
  "phoneNumber": "08012345678"
}
```

### 4. Verify Transaction

**GET** `/api/payment/verify/{reference}`

**Response:**
```json
{
  "status": true,
  "message": "Verification successful",
  "transaction_id": "123456789",
  "amount": "100000",
  "currency": "NGN",
  "status": "success",
  "gateway_response": "Approved",
  "paid_at": "2024-01-15T10:30:00.000Z",
  "channel": "card"
}
```

### 5. Generate Reference

**GET** `/api/payment/reference/generate`

**Response:**
```json
{
  "reference": "REF_ABC123DEF456"
}
```

## Testing

### 1. Test Card Numbers

Use these test card numbers for testing:

- **Visa:** 4084 0840 8408 4081
- **Mastercard:** 5105 1051 0510 5100
- **Verve:** 5061 4604 4123 4567

**CVV:** Any 3 digits (e.g., 123)
**Expiry:** Any future date (e.g., 12/25)

### 2. Test Phone Numbers

For airtime/data testing, use:
- MTN: 08012345678
- Airtel: 07012345678
- Glo: 08051234567
- 9mobile: 08091234567

## Payment Flow

1. **Initialize Payment:** Call the appropriate endpoint (airtime, data, or bill)
2. **Redirect User:** Use the `authorization_url` to redirect user to Paystack checkout
3. **User Pays:** User completes payment on Paystack
4. **Verify Payment:** Call the verify endpoint with the reference
5. **Process Result:** Handle the payment result in your application

## Webhook Callback

The application includes a callback endpoint at `/api/payment/callback` that Paystack will call after payment completion. You can extend this to:

- Update transaction status in your database
- Send confirmation emails/SMS
- Trigger business logic

## Error Handling

The API returns appropriate error messages for:
- Invalid amounts
- Network errors
- Paystack API errors
- Validation errors

## Security Notes

- Never expose your secret key in client-side code
- Always verify transactions server-side
- Use HTTPS in production
- Validate all input data
- Implement proper logging for debugging

## Swagger Documentation

Access the complete API documentation at:
```
http://localhost:8081/swagger-ui.html
```

## Support

For Paystack API issues, refer to:
- [Paystack API Documentation](https://paystack.com/docs/api/)
- [Paystack Support](https://paystack.com/support) 