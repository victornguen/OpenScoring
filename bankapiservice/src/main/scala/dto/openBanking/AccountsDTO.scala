package dto.openBanking

import dto.openBanking.basic._
import zio.json.{DeriveJsonCodec, JsonCodec, jsonField}

case class AccountsDTO(@jsonField("Data") data: AccountsDataDTO,
                       @jsonField("Links") links: LinksDTO,
                       @jsonField("Meta") meta: MetaDTO
                      )
case class AccountsDataDTO(@jsonField("Account") accounts: List[AccountDTO])

case class AccountDTO(
    accountId: String,
    status: String,
    statusUpdateDateTime: String,
    currency: String,
    accountType: String,
    accountSubType: String,
    accountDetails: List[AccountDetailsDTO])

case class AccountDetailsDTO(schemeName: String, identification: String, name: String)

object AccountsDTO {
    implicit val codec: JsonCodec[AccountsDTO] = DeriveJsonCodec.gen[AccountsDTO]
}

object AccountsDataDTO {
    implicit val codec: JsonCodec[AccountsDataDTO] = DeriveJsonCodec.gen[AccountsDataDTO]
}

object AccountDTO {
    implicit val codec: JsonCodec[AccountDTO] = DeriveJsonCodec.gen[AccountDTO]
}

object AccountDetailsDTO {
    implicit val codec: JsonCodec[AccountDetailsDTO] = DeriveJsonCodec.gen[AccountDetailsDTO]
}
