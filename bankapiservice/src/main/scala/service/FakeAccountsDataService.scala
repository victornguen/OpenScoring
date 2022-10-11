package service

import dto.openBanking.basic.{LinksDTO, MetaDTO}
import dto.openBanking._
import zio.{ZIO, Random => ZioRand}

import java.time.format.DateTimeFormatter
import java.time.{Duration, LocalDateTime, ZoneOffset}
import scala.util.{Random => StdRand}

object FakeAccountsDataService {

    def genAccountsDTO: ZIO[Any, Throwable, AccountsDTO] =
        for {
            upperBound <- ZioRand.nextIntBetween(1, 4)

            accountData <- ZIO.foreach((1 to upperBound).toList)(_ => genAccountDTO)
            links       <- genLinks
            meta        <- genMeta
        } yield AccountsDTO(
            data = AccountsDataDTO(accountData),
            links = links,
            meta = meta
        )

    private[service] def genAccountDTO: ZIO[Any, Throwable, AccountDTO] =
        for {
            accountId      <- genAccountId
            status         <- genStatus
            statusUpdateDt <- genDateTime(2015)
            currency       <- genCurrency
            accType        <- genAccountType
            accSubType     <- genAccountSubType
            accDetails     <- genAccountDetails
        } yield AccountDTO(
            accountId = accountId,
            status = status,
            statusUpdateDateTime = statusUpdateDt,
            currency = currency,
            accountType = accType,
            accountSubType = accSubType,
            accountDetails = List(accDetails)
        )

    private[service] def genAccountId =
        ZIO.from(
            StdRand.alphanumeric.filter(_.isDigit).take(5).mkString
        )

    private[service] def genStatus: ZIO[Any, Throwable, String] = randFrom("Enabled", "Disabled", "Deleted", "Pending")

    private[service] def genDateTime(fromYear: Int): ZIO[Any, Throwable, String] = {
        val start = LocalDateTime.of(fromYear, 1, 1, 1, 1, 1, 1)
        val end   = LocalDateTime.now().minus(Duration.ofDays(5))
        ZIO.from(
            LocalDateTime.ofEpochSecond(
                StdRand.between(
                    start.toEpochSecond(ZoneOffset.UTC),
                    end.toEpochSecond(ZoneOffset.UTC)
                ),
                0,
                ZoneOffset.UTC
            ).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) // 2019-01-01T06:06:06+00:00
        )
    }

    private[service] def genCurrency: ZIO[Any, Throwable, String] =
        randFrom("RUB", "USD", "EUR", "JPY", "NZD", "SEK", "GBP", "DKK", "NOK", "SGD", "CZK", "HKD", "MXN", "TRY", "CNH")

    private[service] def genAccountType: ZIO[Any, Throwable, String] = randFrom("Personal", "Business")

    private[service] def genAccountSubType: ZIO[Any, Throwable, String] =
        randFrom("CurrentAccount", "CreditCard", "Loan", "Mortgage", "PrePaidCard", "Savings")

    private[service] def genAccountDetails: ZIO[Any, Throwable, AccountDetailsDTO] = {
        ZIO.from(
            AccountDetailsDTO(
                schemeName = "RU.CBR.AccountNumber",
                identification = StdRand.alphanumeric.filter(_.isDigit).take(20).mkString,
                name = "Дополнительный текущий счет"
            )
        )
    }

    private[service] def genMeta: ZIO[Any, Throwable, MetaDTO] = ZIO.from(MetaDTO(1, None, None))

    private[service] def genLinks: ZIO[Any, Throwable, LinksDTO] =
        for {
            link <- randFrom("https://bank.ru/open-banking/v3.1/aisp/accounts/", "https://api.alphabank.com/open-banking/v3.1/aisp/accounts/22289/balances/")
        } yield LinksDTO(link)

    private[service] def randFromList[A](list: List[A]): ZIO[Any, Throwable, A] =
        ZioRand.shuffle(list).head
            .orElseFail(new Throwable("list is empty"))

    private[service] def randFrom[A](elems: A*): ZIO[Any, Throwable, A] = randFromList(elems.toList)

}
