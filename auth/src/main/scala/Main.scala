import auth.Routes
import zhttp.http._
import zhttp.service.Server
import zio._

object Main extends ZIOAppDefault {

    override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = {
        program
    }

    def app: Http[Any, Nothing, Request, Response] = Routes.app

    val program: ZIO[Any, Throwable, ExitCode] = for {
        _ <- Console.printLine(s"Starting server on http://localhost:8070")
        _ <- Server.start(8070, app)
    } yield ExitCode.success

}
