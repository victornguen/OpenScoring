package com.scalalazy.mainservice.model.dto

import zio.json.{DeriveJsonCodec, DeriveJsonDecoder, DeriveJsonEncoder, JsonCodec, JsonDecoder, JsonEncoder}


/** Кредитный тариф состоит из:
 *   - нижней и верхней границ получаемой суммы
 *   - кредитной ставки
 *   - нижней и верхней границы получения кредита
 */

case class CreditApplicationResultDTO(amountLowerBound: BigDecimal, amountUpperBound: BigDecimal, percentage: Double, timeLimitMonthLower: Month, timeLimitMonthUpper: Month)

object CreditApplicationResultDTO {
//    implicit val codec: JsonCodec[CreditApplicationResultDTO] = DeriveJsonCodec.gen[CreditApplicationResultDTO]

    implicit val decoder = DeriveJsonDecoder.gen[CreditApplicationResultDTO]
    implicit val encoder = DeriveJsonEncoder.gen[CreditApplicationResultDTO]
//    implicit val decoder = JsonDecoder[List[Json]].map(list => list.map(ca => CreditApplicationResultDTO(ca)))
}

