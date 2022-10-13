package creditRules.storage

import creditRules.data.CreditRule
import zio.{Random, Ref, Task, ZLayer}

import java.util.UUID
import scala.collection._

case class InMemoryCreditRulesStorage(storage: Ref[mutable.Map[UUID, CreditRule]]) extends CreditRulesStorage {
    override def addRule(rule: CreditRule): Task[String] = for {
        uuid <- Random.nextUUID
        _    <- storage.getAndUpdate(_.addOne(uuid, rule))
    } yield uuid.toString

    override def getRules: Task[List[CreditRule]] = storage.get.map(_.values.toList)
}

object InMemoryCreditRulesStorage {
    def live: ZLayer[Any, Nothing, InMemoryCreditRulesStorage] =
        ZLayer.fromZIO(Ref.make(mutable.Map.empty[UUID, CreditRule]).map(new InMemoryCreditRulesStorage(_)))

}
