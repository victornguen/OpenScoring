package providers

import com.typesafe.config.{Config, ConfigFactory, ConfigObject}
import zio.{Task, ULayer, ZIO, ZLayer}

import scala.jdk.CollectionConverters.MapHasAsScala

trait ConfigProvider {
    def loadConfig(path: String): Task[Config]

    def config: Task[Config]

    def openApiConfig: Task[Config]

    def appConfig: Task[Config]
}

object ConfigProvider {
    val layer: ULayer[ConfigProviderImpl] = ZLayer.succeed(ConfigProviderImpl())

    def config: ZIO[ConfigProvider, Throwable, Config] = ZIO.serviceWithZIO[ConfigProvider](_.config)

    def openApiConfig: ZIO[ConfigProvider, Throwable, Config] = ZIO.serviceWithZIO[ConfigProvider](_.openApiConfig)

    def appConfig: ZIO[ConfigProvider, Throwable, Config] = ZIO.serviceWithZIO[ConfigProvider](_.appConfig)
}

final case class ConfigProviderImpl(configName: String = "application.conf") extends ConfigProvider {
    lazy val config: Task[Config] = ZIO.succeed(ConfigFactory.load(configName))

    def loadConfig(path: String): Task[Config] = ZIO.succeed(ConfigFactory.load(path))

    def openApiConfig: Task[Config] = config.map(_.getConfig("openApi"))

    def appConfig: Task[Config] = config.map(_.getConfig("app"))

}
