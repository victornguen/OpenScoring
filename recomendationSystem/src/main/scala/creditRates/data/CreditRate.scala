package creditRates.data

import utils.Years
import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

/** Кредитный тариф состоит из:
  *   - нижней и верхней границ получаемой суммы
  *   - кредитной ставки
  *   - нижней и верхней границы получения кредита
  */
case class CreditRate(amountLowerBound: BigDecimal, amountUpperBound: BigDecimal, percentage: Double, timeLimitYearsLower: Years, timeLimitYearsUpper: Years)

object CreditRate {
    implicit val encoder: JsonEncoder[CreditRate] = DeriveJsonEncoder.gen[CreditRate]
    implicit val decoder: JsonDecoder[CreditRate] = DeriveJsonDecoder.gen[CreditRate]
}
