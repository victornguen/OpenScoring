package creditRequests.storage

import creditRates.data.CreditRate
import creditRequests.data.CreditRequest
import creditRules.storage.CreditRulesStorage
import zio.{Task, ZIO}

trait CreditRequestStorage {

  def addRequest(request: CreditRequest): Task[String]

  def getRequests: Task[List[CreditRequest]]

  def makeDecision(request: CreditRequest): ZIO[CreditRulesStorage with CreditRequestStorage, Throwable, List[CreditRate]]

}

object CreditRequestStorage {

  def addRequest(request: CreditRequest): ZIO[CreditRequestStorage, Throwable, String] =
    ZIO.serviceWithZIO[CreditRequestStorage](_.addRequest(request))

  def getRequests: ZIO[CreditRequestStorage, Throwable, List[CreditRequest]] =
    ZIO.serviceWithZIO[CreditRequestStorage](_.getRequests)

  def makeDecision(request: CreditRequest): ZIO[CreditRulesStorage with CreditRequestStorage, Throwable, List[CreditRate]] =
    ZIO.serviceWithZIO[CreditRequestStorage](_.makeDecision(request))

}
