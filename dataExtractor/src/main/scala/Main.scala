import providers.ConfigProvider
import service.controllers.AccountInfoController
import service.controllers.mock.AccountInfoControllerMock
import service.services.{UrlService, UrlServiceDefaultImpl}
import zhttp.http._
import zhttp.service.{ChannelFactory, Client, EventLoopGroup, Server}
import zio._
import zio.Console.printLine
import zhttp.service.EventLoopGroup

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
            _      <- Console.printLine("")
            _      <- Server.start(port, allRoutes)
        } yield ExitCode.success

    def allRoutes =
        AccountInfoControllerMock.routes ++
        AccountInfoController.routes

}
