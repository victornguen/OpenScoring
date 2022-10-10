import auth.Routes
import service.services.{UserRepository, UserRepositoryTestImpl}
import zhttp.http._
import zhttp.service.Server
import zio._

object Main extends ZIOAppDefault {

    override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = program.provide(
        UserRepository.layer
    )

    def app= Routes.app

//    val userRepository: UserRepositoryTestImpl = UserRepositoryTestImpl()
//    userRepository.addUser("admin", "admin")

    val program: ZIO[UserRepository, Throwable, ExitCode] = for {
        _ <- UserRepository.addUser("admin", "admin")
        _ <- Console.printLine(s"Starting server on http://localhost:8070")
        _ <- Server.start(8070, app)
    } yield ExitCode.success

}
