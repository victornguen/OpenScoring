package service.dto

import zio.json.JsonCodec

case class GetAccountsDTO(
    bankId: String,
    bearerToken: String,
    customerIpAddress: String,
    authDate: String,
    interactionId: String)

object GetAccountsDTO{
    implicit val codec: JsonCodec[GetAccountsDTO] = zio.json.DeriveJsonCodec.gen[GetAccountsDTO]
}