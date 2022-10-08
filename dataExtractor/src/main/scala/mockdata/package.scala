package object mockdata {

    val accounts: String =
        """{
          |  "Data": {
          |    "Account": [
          |      {
          |        "accountId": "23489",
          |        "status": "Enabled",
          |        "statusUpdateDateTime": "2019-01-01T06:06:06+00:00",
          |        "currency": "RUB",
          |        "accountType": "Personal",
          |        "accountSubType": "CurrentAccount",
          |        "accountDetails": [
          |          {
          |            "schemeName": "RU.CBR.AccountNumber",
          |            "identification": "40817810621234567890",
          |            "name": "Основной текущий счет"
          |          }
          |        ]
          |      },
          |      {
          |        "accountId": "31820",
          |        "status": "Enabled",
          |        "statusUpdateDateTime": "2019-01-01T06:06:06+00:00",
          |        "currency": "RUB",
          |        "accountType": "Personal",
          |        "accountSubType": "CurrentAccount",
          |        "accountDetails": [
          |          {
          |            "schemeName": "RU.CBR.AccountNumber",
          |            "identification": "40817810621234562345",
          |            "name": "Дополнительный текущий счет"
          |          }
          |        ]
          |      }
          |    ]
          |  },
          |  "Links": {
          |    "Self": "https://bank.ru/open-banking/v3.1/aisp/accounts/"
          |  },
          |  "Meta": {
          |    "TotalPages": 1
          |  }
          |}""".stripMargin

    val balances: String =
        """{
          |  "Data": {
          |    "Balance": [
          |      {
          |        "accountId": "11139",
          |        "Amount": {
          |          "amount": "13430.00",
          |          "currency": "RUB"
          |        },
          |        "CreditDebitIndicator": "Credit",
          |        "Type": "OpeningAvailable",
          |        "DateTime": "2019-09-15T14:33:07+00:00",
          |        "CreditLine": [
          |          {
          |            "Included": true,
          |            "Amount": {
          |              "amount": "4000.00",
          |              "currency": "RUB"
          |            },
          |            "Type": "Pre-Agreed"
          |          }
          |        ]
          |      }
          |    ]
          |  },
          |  "Links": {
          |    "Self": "https://api.alphabank.com/open-banking/v3.1/aisp/accounts/22289/balances/"
          |  },
          |  "Meta": {
          |    "TotalPages": 1
          |  }
          |}""".stripMargin

}
