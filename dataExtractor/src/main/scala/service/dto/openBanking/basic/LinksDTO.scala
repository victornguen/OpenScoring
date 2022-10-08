package service.dto.openBanking.basic

import zio.json.{DeriveJsonCodec, JsonCodec, jsonField}

case class LinksDTO(@jsonField("Self") self: String)

object LinksDTO {
    implicit val codec: JsonCodec[LinksDTO] = DeriveJsonCodec.gen[LinksDTO]
}
