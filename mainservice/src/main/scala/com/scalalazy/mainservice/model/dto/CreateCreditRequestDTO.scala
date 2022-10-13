package com.scalalazy.mainservice.model.dto
import zio.json.JsonCodec

case class CreateCreditRequestDTO(
    amount: BigDecimal,
    timeLimitMonth: Month,
    accountBalance: BigDecimal,
    monthIncome: BigDecimal)

case class Month(value: Double) extends AnyVal

object CreateCreditRequestDTO {
    implicit val codec: JsonCodec[CreateCreditRequestDTO] = zio.json.DeriveJsonCodec.gen[CreateCreditRequestDTO]
}

object Month {
    implicit val codec: JsonCodec[Month] = zio.json.DeriveJsonCodec.gen[Month]
}
