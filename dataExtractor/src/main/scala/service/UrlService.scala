package service

import providers.ConfigProvider
import service.syntax.ConfigObjectOps
import zio.{RIO, ZIO, ZLayer}

trait UrlService {

    /** get default url of service */
    def getDefaultUrls: RIO[ConfigProvider, Map[String, String]]
}

object UrlService {
    val layer: ZLayer[Any, Nothing, UrlServiceDefaultImpl] = ZLayer.succeed(UrlServiceDefaultImpl())

    def getDefaultUrls: ZIO[ConfigProvider with UrlService, Throwable, Map[String, String]] = ZIO.serviceWithZIO[UrlService](_.getDefaultUrls)
}

final case class UrlServiceDefaultImpl() extends UrlService {

    override def getDefaultUrls: RIO[ConfigProvider, Map[String, String]] =
        for {
            cp <- ZIO.service[ConfigProvider]
            r  <- cp.openApiConfig
        } yield r.getObject("urls").toMap(_.toString)

}
