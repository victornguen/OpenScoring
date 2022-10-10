package creditRequests.storage

import creditRequests.data.CreditRequest
import zio.{Task, ZIO}

trait CreditRequestStorage {

    def addRequest(request: CreditRequest): Task[String]

    def getRequests: Task[List[CreditRequest]]

}

object CreditRequestStorage {

    def addRequest(request: CreditRequest): ZIO[CreditRequestStorage, Throwable, String] =
        ZIO.serviceWithZIO[CreditRequestStorage](_.addRequest(request))

    def getRequests: ZIO[CreditRequestStorage, Throwable, List[CreditRequest]] =
        ZIO.serviceWithZIO[CreditRequestStorage](_.getRequests)

}
