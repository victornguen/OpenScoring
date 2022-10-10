package creditRequests.storage

import creditRequests.data.CreditRequest
import zio.{Random, Ref, Task, ZLayer}
import scala.collection._

import java.util.UUID

case class InMemoryCreditRequestsStorage(storage: Ref[mutable.Map[UUID, CreditRequest]])
    extends CreditRequestStorage {
    override def addRequest(request: CreditRequest): Task[String] = for {
        uuid <- Random.nextUUID
        _    <- storage.getAndUpdate(_.addOne(uuid, request))
    } yield uuid.toString

    override def getRequests: Task[List[CreditRequest]] = storage.get.map(_.values.toList)
}

object InMemoryCreditRequestsStorage {

    def live: ZLayer[Any, Nothing, InMemoryCreditRequestsStorage] =
        ZLayer.fromZIO(Ref.make(mutable.Map.empty[UUID, CreditRequest])
            .map(new InMemoryCreditRequestsStorage(_)))

}
