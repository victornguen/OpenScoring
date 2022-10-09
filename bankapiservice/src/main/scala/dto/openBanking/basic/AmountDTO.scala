package dto.openBanking.basic

import zio.json.{DeriveJsonCodec, JsonCodec}

case class AmountDTO(amount: String, currency: String)

object AmountDTO {
    implicit val codec: JsonCodec[AmountDTO] = DeriveJsonCodec.gen[AmountDTO]
}
