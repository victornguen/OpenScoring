package service.dto.openBanking.basic

import zio.json.{DeriveJsonCodec, JsonCodec}

case class LinksDTO(self: String)

object LinksDTO {
    implicit val codec: JsonCodec[LinksDTO] = DeriveJsonCodec.gen[LinksDTO]
}
