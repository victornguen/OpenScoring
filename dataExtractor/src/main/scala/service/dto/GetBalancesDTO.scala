package service.dto

import zio.json.JsonCodec

case class GetBalancesDTO(
    bankId: String,
    accountId: String,
    bearerToken: String,
    customerIpAddress: String,
    authDate: String)

object GetBalancesDTO {
    implicit val codec: JsonCodec[GetBalancesDTO] = zio.json.DeriveJsonCodec.gen[GetBalancesDTO]
}
