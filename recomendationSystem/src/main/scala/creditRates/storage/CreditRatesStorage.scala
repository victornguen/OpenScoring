package creditRates.storage

import creditRates.data.CreditRate
import zio.{Task, ZIO}

trait CreditRatesStorage {

    def addRate(rate: CreditRate): Task[String]

    def getRates: Task[List[CreditRate]]

}

object CreditRatesStorage {

    def addRate(rate: CreditRate): ZIO[CreditRatesStorage, Throwable, String] =
        ZIO.serviceWithZIO[CreditRatesStorage](_.addRate(rate))

    def getRates: ZIO[CreditRatesStorage, Throwable, List[CreditRate]] = ZIO.serviceWithZIO[CreditRatesStorage](_.getRates)

}
