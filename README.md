# Oxlin | Linxo Integration in Java
This documentation shows how to integrate Linxo using Java Springboot
[Doc: Quickstart | Developer Portal ](https://developers.linxoconnect.com/docs/direct-payment-api-quickstart/#1-client-authentication)

## Flow Diagram
![linxo_flow](https://github.com/busaridwan/linxo/assets/32122386/9c2c879f-4cf8-4567-a568-accbff8bb234)

### 0. Authorize Account
This is to whitelist the company account.

`POST` : `http://localhost:8080/linxo/account`

`Request`
```
{
    "identification": {
        "schema": "SEPA",
        "iban": "FR8530003000307599775722N09",
        "name": "MY SANDBOX COMPANY"
    },
    "entity": {
        "type": "COMPANY",
        "company_name": "MY SANDBOX COMPANY",
        "national_identification": "99999999999999",
        "country": "FR"
    }
}
```

### 1. Bearer Token
This is to generate a JWToken 

`Request`

`GET` : `http://localhost:8080/linxo/token`

`Response`
```
{
    "scope": "profile email",
    "access_token": "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJDMk9kM1k3VEJOZWtURXlPc2hMNUxHRUUtakdtNVJYQmh1R0dIOFNDUDJvIn0.eyJleHAiOjE3MDUzMjAwNjgsImlhdCI6MTcwNTMxMTQyOCwianRpIjoiYzI5MGVmZjgtYjBiNC00ZjcxLWE4MDItZTk3NzhkZjI5N2U3IiwiaXNzIjoiaHR0cHM6Ly9rZXljbG9hay5veGxpbi5pby9hdXRoL3JlYWxtcy9veGxpbi1wcm9kLXBheSIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiJkMmI3M2U1Ni01OGI2LTQ4YTctOTIwMy1iNDAwZTM4YzU5NzciLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJBZmF5WUN1dUtuR0lfQTFRXzNWZUNXa0NVeXNhIiwiYWNyIjoiMSIsInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJkZWZhdWx0LXJvbGVzLW94bGluLXByb2QtcGF5Iiwib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoicHJvZmlsZSBlbWFpbCIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwiY2xpZW50SWQiOiJBZmF5WUN1dUtuR0lfQTFRXzNWZUNXa0NVeXNhIiwiY2xpZW50SG9zdCI6IjE1NC4xMTMuMTg3LjE3OSIsImxpbnhvX21vZGUiOiJTQU5EQk9YIiwicHJlZmVycmVkX3VzZXJuYW1lIjoic2VydmljZS1hY2NvdW50LWFmYXl5Y3V1a25naV9hMXFfM3ZlY3drY3V5c2EiLCJjbGllbnRBZGRyZXNzIjoiMTU0LjExMy4xODcuMTc5IiwibGlueG9fdGVuYW50X2lkIjoiZGVtbyJ9.hoS6oDehGvQWdqN8vdnS_wLwFT0VbjGCm2xIJ5FCQit0bjV2woY-L8Ji2kEsHsfKkdyG6oTEgcD8E1RYuj79SoGDBecB7WXi5duj1TMDKhrrC5fBUwLSaFznXf4AhBsAumRHSPb4qyWdie5dTzPRZ3KkC-9YpVowl08Xi2MNES4ThtAFX8XrZtbgHhlHIoDBerrBW0t7nC627B3Y8ZNI-W1tEwA9Vs0l5V0GYGCubk7nVS09IlbTImkCDpSiK3dHfFdQo5ujp9sfYAJxxM-3EIfitj3VSmhBfRCk-n7HSbCG8To2s6da3IfqdSXpflV6gj8bgBtQMzFQH9Zp7S7hIg",
    "expires_in": "8640",
    "refresh_expires_in": "0",
    "token_type": "Bearer",
    "not-before-policy": "0"
}
```

### 2. Transaction Payment
This is the payment enddpoint - returns a url whihc is display in an iframe/webview for customer to interact with.

`POST` : `http://localhost:8080/linxo/pay`

`Request`

```
{
  "requestId": "fe12a292-2e1b-47e5-af0d-35f4144b09f5",
  "instructions": [
    {
      "amount": 1.42,
      "currency": "EUR",
      "label": "My Linxo Connect Payment Test",
      "beneficiary": {
        "schema": "SEPA",
        "iban": "FR8530003000307599775722N09",
        "name": "Arthur Dent"
      }
    }
  ]
}
```

### 3. Check Transaction Status
I have made this a `POST` request based on my FrontEnd request. You can make this a `GET` request with `orderId` as the request parameter.

`POST` : `http://localhost:8080/linxo/status`

`Request`

```
{
    "orderId": "1ca7ff92-4aae-4080-a81e-959166a36a20"
}
```

`Response`

```
{
    "id": "1ca7ff92-4aae-4080-a81e-959166a36a20",
    "instructions": [
        {
            "amount": "1.42",
            "currency": "EUR",
            "label": "My Linxo Connect Payment Test",
            "beneficiary": {
                "schema": "SEPA",
                "iban": "FR8530003000307599775722N09",
                "name": "Arthur Dent",
                "bic": "SOGEFRPPXXX"
            }
        }
    ],
    "instant_payment": "NO",
    "selected_capability": "SINGLE_PAYMENT",
    "order_status": "NEW",
    "creation_date": "2024-01-15T11:22:36.26332307Z",
    "auth_url": "https://pay.oxlin.io/v1/authorize/1ca7ff92-4aae-4080-a81e-959166a36a20?client_id=AfayYCuuKnGI_A1Q_3VeCWkCUysa",
    "redirect_url": "https://developers.linxoconnect.com/docs/direct-payment-api-quickstart/#1-client-authentication",
    "payer_time_zone": "Europe/Paris"
}
```
