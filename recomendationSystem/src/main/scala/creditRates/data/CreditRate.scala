package creditRates.data

import utils.Month
import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

/** Кредитный тариф состоит из:
  *   - нижней и верхней границ получаемой суммы
  *   - кредитной ставки
  *   - нижней и верхней границы получения кредита
  */
case class CreditRate(amountLowerBound: BigDecimal, amountUpperBound: BigDecimal, percentage: Double, timeLimitMonthLower: Month, timeLimitMonthUpper: Month)

object CreditRate {
    implicit val encoder: JsonEncoder[CreditRate] = DeriveJsonEncoder.gen[CreditRate]
    implicit val decoder: JsonDecoder[CreditRate] = DeriveJsonDecoder.gen[CreditRate]
}
