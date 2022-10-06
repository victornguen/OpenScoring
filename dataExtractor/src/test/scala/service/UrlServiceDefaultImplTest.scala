package service

import providers.{ConfigProvider, ConfigProviderImpl}
import zio.{RIO, Scope, ULayer, ZIO, ZLayer}
import zio.test._

object UrlServiceDefaultImplTest extends ZIOSpecDefault {

    val configProviderTestLayer: ULayer[ConfigProviderImpl] = ZLayer.succeed(ConfigProviderImpl("application.test.conf"))

    override def spec: Spec[TestEnvironment with Scope, Any] =
        suite("UrlServiceDefaultImpl Test")(
            test("get default api urls") {
                for {
                    urlService <- ZIO.service[UrlService]
                    urls       <- urlService.getDefaultUrls
                    _ <- zio.Console.printLine(urls)
                } yield assertTrue(urls.nonEmpty)
            }
        ).provide(UrlService.layer, configProviderTestLayer)

}
