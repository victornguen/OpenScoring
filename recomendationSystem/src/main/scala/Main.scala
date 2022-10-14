import Routes.Routes
import creditRates.storage.InMemoryCreditRateStorage
import creditRequests.storage.InMemoryCreditRequestsStorage
import creditRules.storage.InMemoryCreditRulesStorage
import zhttp.service.Server
import zio._

object Main extends ZIOAppDefault {
    override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = Server
        .start(8999, Routes.rulesRoutes ++ Routes.rateRoutes ++ Routes.creditRequestRoutes)
        .provide(InMemoryCreditRateStorage.live, InMemoryCreditRulesStorage.live, InMemoryCreditRequestsStorage.live)

}
