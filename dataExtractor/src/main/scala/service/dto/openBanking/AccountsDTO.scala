package service.dto.openBanking

import service.dto.openBanking.basic._
import zio.json.{DeriveJsonCodec, JsonCodec}

case class AccountsDTO(data: Data, links: LinksDTO, meta: MetaDTO)
case class Data(account: List[Account])

case class Account(
    accountId: String,
    status: String,
    statusUpdateDateTime: String,
    currency: String,
    accountType: String,
    accountSubType: String,
    accountDetails: List[AccountDetails])

case class AccountDetails(schemeName: String, identification: String, name: String)

object AccountsDTO {
    implicit val codec: JsonCodec[AccountsDTO] = DeriveJsonCodec.gen[AccountsDTO]
}

object Data {
    implicit val codec: JsonCodec[Data] = DeriveJsonCodec.gen[Data]
}

object Account {
    implicit val codec: JsonCodec[Account] = DeriveJsonCodec.gen[Account]
}

object AccountDetails {
    implicit val codec: JsonCodec[AccountDetails] = DeriveJsonCodec.gen[AccountDetails]
}
