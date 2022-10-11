# Структура JSON ответов

## `/accounts`

```http request
GET /accounts HTTP/1.1
Authorization: Bearer Az34SAOJkldf
x-fapi-auth-date: Sun, 8 Sep 2019 23:43:12 GMT
x-fapi-customer-ip-address: 134.21.122.22
x-fapi-interaction-id: 93bac548-f5fe-6780-b106-880a5018460d
Accept: application/json
```

```JSON
{
  "Data": {
    "Account": [
      {
        "accountId": "23489",
        "status": "Enabled",
        "statusUpdateDateTime": "2019-01-01T06:06:06+00:00",
        "currency": "RUB",
        "accountType": "Personal",
        "accountSubType": "CurrentAccount",
        "accountDetails": [
          {
            "schemeName": "RU.CBR.AccountNumber",
            "identification": "40817810621234567890",
            "name": "Основной текущий счет"
          }
        ]
      },
      {
        "accountId": "31820",
        "status": "Enabled",
        "statusUpdateDateTime": "2019-01-01T06:06:06+00:00",
        "currency": "RUB",
        "accountType": "Personal",
        "accountSubType": "CurrentAccount",
        "accountDetails": [
          {
            "schemeName": "RU.CBR.AccountNumber",
            "identification": "40817810621234562345",
            "name": "Дополнительный текущий счет"
          }
        ]
      }
    ]
  },
  "Links": {
    "self": "https://bank.ru/open-banking/v3.1/aisp/accounts/"
  },
  "Meta": {
    "totalPages": 1
  }
}
```

## /accounts/{accountId}/balances

```http request
GET /accounts/11139/balances HTTP/1.1
Authorization: Bearer Sd6753AOJweae
x-fapi-auth-date: Sun, 15 Sep 2019 11:22:31 GMT
x-fapi-customer-ip-address: 10.11.21.39
x-fapi-interaction-id: 23bac548-d2de-4567-b106-880a5018460d
Accept: application/json
```

```JSON
{
  "Data": {
    "Balance": [
      {
        "accountId": "11139",
        "Amount": {
          "amount": "13430.00",
          "currency": "RUB"
        },
        "CreditDebitIndicator": "Credit",
        "Type": "OpeningAvailable",
        "DateTime": "2019-09-15T14:33:07+00:00",
        "CreditLine": [
          {
            "Included": true,
            "Amount": {
              "Amount": "4000.00",
              "Currency": "RUB"
            },
            "Type": "Pre-Agreed"
          }
        ]
      }
    ]
  },
  "Links": {
    "Self": "https://api.alphabank.com/open-banking/v3.1/aisp/accounts/22289/balances/"
  },
  "Meta": {
    "TotalPages": 1
  }
}
```