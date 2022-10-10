package creditRequests.data

import utils.Years
import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

/** Запрос пользователя на кредит состоит из:
  *   - желаемой суммы
  *   - срока кредита
  *   - текущий остаток на счете
  *   - месячный доход
  */
case class CreditRequest(amount: BigDecimal,
                         timeLimitYears: Years,
                         accountBalance: BigDecimal,
                         monthIncome: BigDecimal
                        )

object CreditRequest {
    implicit val encoder: JsonEncoder[CreditRequest] = DeriveJsonEncoder.gen[CreditRequest]
    implicit val decoder: JsonDecoder[CreditRequest] = DeriveJsonDecoder.gen[CreditRequest]
}
