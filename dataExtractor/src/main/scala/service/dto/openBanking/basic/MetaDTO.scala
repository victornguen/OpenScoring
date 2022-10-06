package service.dto.openBanking.basic

import zio.json.JsonCodec

case class MetaDTO(totalPages: Int, firstAvailableDateTime: Option[String], lastAvailableDateTime: Option[String])

object MetaDTO{
    implicit val codec: JsonCodec[MetaDTO] = zio.json.DeriveJsonCodec.gen[MetaDTO]
}
