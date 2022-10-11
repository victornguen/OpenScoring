import com.typesafe.config.ConfigFactory
import routes.{AccountRoutes, BalancesRoutes}
import zhttp.http.{Http, Request, Response}
import zhttp.service.Server
import zio._
import zio.Console.printLine

object Main extends ZIOAppDefault {
    override def run: ZIO[Environment with ZIOAppArgs with Scope, Any, Any] = app

    val app = for {
        port <- ZIO.from(ConfigFactory.load().getInt("app.port"))
        _    <- Console.printLine(s"Starting service on http://localhost:$port")
        _    <- Server.start(port, routes)
    } yield ExitCode.success

    val routes: Http[Any, Throwable, Request, Response] = BalancesRoutes.all ++
        AccountRoutes.all

}
