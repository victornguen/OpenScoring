package service.dto

import zio.json.{DeriveJsonCodec, JsonCodec}

case class GetAccountsDTO(
    bankId: String,
    bearerToken: String,
    customerIpAddress: String,
    authDate: String,
    interactionId: String)

object GetAccountsDTO{
    implicit val codec: JsonCodec[GetAccountsDTO] = DeriveJsonCodec.gen[GetAccountsDTO]
}