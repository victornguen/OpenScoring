package com.scalalazy.mainservice.model.config

import cats.syntax.all._
import ciris._
import ciris.refined._
import eu.timepit.refined.api.Refined
import eu.timepit.refined.collection.NonEmpty
import eu.timepit.refined.types.net.UserPortNumber

import java.net.InetAddress

final case class Application(
    server: Server,
    database: Database
)

object Application {
    implicit val inetAddressDecoder: ConfigDecoder[String, InetAddress] =
        ConfigDecoder[String, String].map(InetAddress.getByName)

    private val config = (
      env("server.host").as[InetAddress],
      env("server.port").as[UserPortNumber],
      env("dataSource.className").as[Refined[String, NonEmpty]],
      env("dataSource.url").as[Refined[String, NonEmpty]],
      env("dataSource.user").as[Refined[String, NonEmpty]],
      env("dataSource.password").as[Refined[String, NonEmpty]]
    ).parMapN { (url, port, className, dataSourceUrl, user, password) =>
        Application(Server(url, port), Database(className, dataSourceUrl, user, password))
    }

    def appConfig = {}

    def getConfig: ConfigValue[Effect, Application] = config
}
