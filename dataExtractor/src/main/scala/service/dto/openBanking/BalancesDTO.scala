package service.dto.openBanking

import service.dto.openBanking.basic._
import zio.json.{DeriveJsonCodec, JsonCodec, jsonField}

case class BalancesDTO(
    @jsonField("Data") data: BalanceDataDTO,
    @jsonField("Links") links: LinksDTO,
    @jsonField("Meta") meta: MetaDTO)

case class BalanceDataDTO(@jsonField("Balance") balance: Seq[BalanceDTO])

case class BalanceDTO(
    accountId: String,
    @jsonField("Amount") amount: AmountDTO,
    @jsonField("CreditDebitIndicator") creditDebitIndicator: String,
    @jsonField("Type") balanceType: String,
    @jsonField("DateTime") dateTime: String,
    @jsonField("CreditLine") creditLine: Seq[CreditLineDTO])

case class CreditLineDTO(
    @jsonField("Included") included: Boolean,
    @jsonField("Amount") amount: AmountDTO,
    @jsonField("Type") lineType: String)


object BalanceDataDTO {
    implicit val codec: JsonCodec[BalanceDataDTO] = DeriveJsonCodec.gen[BalanceDataDTO]
}

object CreditLineDTO {
    implicit val codec: JsonCodec[CreditLineDTO] = DeriveJsonCodec.gen[CreditLineDTO]
}

object BalanceDTO {
    implicit val codec: JsonCodec[BalanceDTO] = DeriveJsonCodec.gen[BalanceDTO]
}

object BalancesDTO {
    implicit val codec: JsonCodec[BalancesDTO] = DeriveJsonCodec.gen[BalancesDTO]
}