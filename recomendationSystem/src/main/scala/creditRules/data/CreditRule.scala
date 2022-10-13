package creditRules.data

import creditRates.data.CreditRate
import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

/** Кредитное правило состоит из:
  *   - условий кредитных
  *   - коллекции кредитных тарифов
  */
// TODO: дописать кредитные условия
case class CreditRule(creditRates: List[CreditRate])

object CreditRule {
    implicit val encoder: JsonEncoder[CreditRule] = DeriveJsonEncoder.gen[CreditRule]
    implicit val decoder: JsonDecoder[CreditRule] = DeriveJsonDecoder.gen[CreditRule]
}
