package service

import dto.openBanking._
import dto.openBanking.basic.AmountDTO
import service.FakeAccountsDataService._
import zio.{Random, ZIO}

object FakeBalancesDataService {

    def genBalancesDTO(accountId: String): ZIO[Any, Throwable, BalancesDTO] = {
        for {
            balance <- genBalanceDTO(accountId)
            links   <- genLinks
            meta    <- genMeta
        } yield BalancesDTO(
            data = BalanceDataDTO(Seq(balance)),
            links = links,
            meta = meta
        )
    }

    private[service] def genBalanceDTO(accountId: String): ZIO[Any, Throwable, BalanceDTO] =
        for {
            amount      <- genAmountDTO
            cdIndicator <- genCreditDebitIndicator
            balanceType <- genBalanceType
            dt          <- genDateTime(2015)
            cl          <- genCreditLineDTO
        } yield BalanceDTO(
            accountId = accountId,
            amount = amount,
            creditDebitIndicator = cdIndicator,
            balanceType = balanceType,
            dateTime = dt,
            creditLine = Seq(cl)
        )

    private[service] def genAmountDTO: ZIO[Any, Throwable, AmountDTO] =
        for {
            amount   <- Random.nextIntBetween(10000, 10000000)
            currency <- genCurrency
        } yield AmountDTO(
            amount = amount.toString,
            currency = currency
        )

    private[service] def genCreditDebitIndicator: ZIO[Any, Throwable, String] =
        randFrom("Credit", "Debit")

    private[service] def genBalanceType: ZIO[Any, Throwable, String] =
        randFrom(
            "ClosingAvailable",
            "ClosingBooked",
            "ClosingCleared",
            "Expected",
            "ForwardAvailable",
            "Information",
            "InterimAvailable",
            "InterimBooked",
            "InterimCleared",
            "OpeningAvailable",
            "OpeningBooked",
            "OpeningCleared",
            "PreviouslyClosedBooked"
        )

    private[service] def genCreditLineType: ZIO[Any, Throwable, String] = randFrom("Pre-Agreed", "Agreed", "Dismissed")

    private[service] def genCreditLineDTO: ZIO[Any, Throwable, CreditLineDTO] = for {
        amount   <- genAmountDTO
        included <- Random.nextBoolean
        clType   <- genCreditLineType
    } yield CreditLineDTO(included, amount, clType)

}
