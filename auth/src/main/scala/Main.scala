import auth.Routes
import zhttp.service.Server
import zio._

object Main extends ZIOAppDefault {

    override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] =
        Server.start(8070, Routes.app)

}
