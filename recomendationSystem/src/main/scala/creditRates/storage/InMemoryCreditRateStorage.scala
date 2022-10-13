package creditRates.storage

import creditRates.data.CreditRate
import zio.{Random, Ref, Task, ZLayer}

import java.util.UUID
import scala.collection._

case class InMemoryCreditRateStorage(storage: Ref[mutable.Map[UUID, CreditRate]]) extends CreditRatesStorage {
    override def addRate(rate: CreditRate): Task[String] = for {
        uuid <- Random.nextUUID
        _    <- storage.getAndUpdate(_.addOne(uuid, rate))
    } yield uuid.toString

    override def getRates: Task[List[CreditRate]] = storage.get.map(_.values.toList)
}

object InMemoryCreditRateStorage {
    def live: ZLayer[Any, Nothing, InMemoryCreditRateStorage] =
        ZLayer.fromZIO(Ref.make(mutable.Map.empty[UUID, CreditRate]).map(new InMemoryCreditRateStorage(_)))

}
