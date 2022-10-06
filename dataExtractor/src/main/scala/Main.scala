import providers.ConfigProvider
import service.{UrlService, UrlServiceDefaultImpl}
import zio._
import zio.Console.printLine

object Main extends ZIOAppDefault {
    override def run =
        app.provide(
            UrlService.layer,
            ConfigProvider.layer
        )

    def app =
        for {
            cp <- ZIO.service[ConfigProvider]
            conf <- cp.loadConfig("openApi")
        } yield println(cp)

}
