package providers

import service.UrlService
import service.UrlServiceDefaultImplTest.{suite, test}
import zio.{RIO, Scope, ZIO, ZLayer}
import zio.test.{Spec, TestEnvironment, ZIOSpecDefault, assertTrue}
import providers.ConfigProvider
import zio.test._

object ConfigProviderImplTest extends ZIOSpecDefault {
    override def spec: Spec[TestEnvironment with Scope, Any] =
        suite("ConfigProviderImpl Test")(
            test("get all config") {
                for {
                    cp <- ZIO.service[ConfigProvider]
                    config       <- cp.config
                    _ <- zio.Console.printLine(config)
                } yield assertTrue(!config.isEmpty)
            },
            test("get openApi config"){
                for {
                    cp <- ZIO.service[ConfigProvider]
                    openApiConfig       <- cp.openApiConfig
                    _ <- zio.Console.printLine(openApiConfig)
                } yield assertTrue(!openApiConfig.isEmpty)
            }
        ).provide(ZLayer.succeed(ConfigProviderImpl("application.test.conf")))

}


