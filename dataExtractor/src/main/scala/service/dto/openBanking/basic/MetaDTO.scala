package service.dto.openBanking.basic

import zio.json.{JsonCodec, jsonField}

case class MetaDTO(@jsonField("TotalPages") totalPages: Int,
                   @jsonField("FirstAvailableDateTime") firstAvailableDateTime: Option[String],
                   @jsonField("LastAvailableDateTime") lastAvailableDateTime: Option[String])

object MetaDTO{
    implicit val codec: JsonCodec[MetaDTO] = zio.json.DeriveJsonCodec.gen[MetaDTO]
}
