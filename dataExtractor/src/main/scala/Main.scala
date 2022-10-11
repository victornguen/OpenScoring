import providers.ConfigProvider
import service.controllers.AccountInfoController
import service.controllers.mock.AccountInfoControllerMock
import service.services.UrlService
import zhttp.service.{ChannelFactory, EventLoopGroup, Server}
import zio._

object Main extends ZIOAppDefault {
    override def run =
        app.provide(
            UrlService.layer,
            ConfigProvider.layer,
            Scope.default,
            EventLoopGroup.default,
            ChannelFactory.auto
        )

    def app =
        for {
            config <- ConfigProvider.loadConfig("application.conf")
            port    = config.getConfig("app").getInt("port")
            _      <- Console.printLine(s"Starting server on http://localhost:$port")
            _      <- Server.start(port, allRoutes)
        } yield ExitCode.success

    def allRoutes =
        AccountInfoControllerMock.routes ++
        AccountInfoController.routes

}
