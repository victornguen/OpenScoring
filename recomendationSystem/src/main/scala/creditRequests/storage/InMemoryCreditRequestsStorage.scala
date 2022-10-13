package creditRequests.storage

import creditRates.data.CreditRate
import creditRequests.data.CreditRequest
import creditRules.storage.CreditRulesStorage
import zio.{Random, Ref, Task, ZIO, ZLayer}

import java.util.UUID
import scala.collection._

case class InMemoryCreditRequestsStorage(requestStorage: Ref[mutable.Map[UUID, CreditRequest]])
  extends CreditRequestStorage {
  override def addRequest(request: CreditRequest): Task[String] = for {
    uuid <- Random.nextUUID
    _ <- requestStorage.getAndUpdate(_.addOne(uuid, request))
  } yield uuid.toString

  override def getRequests: Task[List[CreditRequest]] = requestStorage.get.map(_.values.toList)

  override def makeDecision(request: CreditRequest): ZIO[CreditRulesStorage with CreditRequestStorage, Throwable, List[CreditRate]] =
    for {
      rules <- CreditRulesStorage.getRules
      listRates = rules.flatMap {
        rule =>
          rule.creditRates
            .filter(rate => rate.amountLowerBound <= request.amount &&
              request.amount <= rate.amountUpperBound &&
              rate.timeLimitMonthLower.value <= request.timeLimitMonth.value &&
              request.timeLimitMonth.value <= rate.timeLimitMonthUpper.value)
      }
    } yield listRates
}

object InMemoryCreditRequestsStorage {

  def live: ZLayer[Any, Nothing, InMemoryCreditRequestsStorage] = ZLayer.fromZIO(
    for {
      req <- Ref.make(mutable.Map.empty[UUID, CreditRequest])
    } yield new InMemoryCreditRequestsStorage(req)
  )

}
