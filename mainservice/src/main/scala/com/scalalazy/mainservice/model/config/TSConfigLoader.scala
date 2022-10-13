package com.scalalazy.mainservice.model.config
import com.typesafe.config.ConfigFactory
import zio.ZIO

import java.net.InetAddress

object TSConfigLoader {

    case class AppConfig(host: InetAddress, port: Int, dataExtractorLink: String, recommendationServiceLink: String)

    def load(configName: String = "application.conf") = ZIO.from {
        val config = ConfigFactory.load(configName)
        AppConfig(
          host = InetAddress.getByName(config.getString("app.host")),
          port = config.getInt("app.port"),
          dataExtractorLink = config.getString("services.dataExtractor"),
          recommendationServiceLink = config.getString("services.recommendationService")
        )
    }

}
