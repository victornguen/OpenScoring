package com.scalalazy.mainservice

import cats.effect.ExitCode
import cats.effect.unsafe.implicits.global
import com.scalalazy.mainservice.Main.creditApplicationResultRoute
import com.scalalazy.mainservice.model.config.{Application, TSConfigLoader}
import com.scalalazy.mainservice.module.CreditApplicationDB.{
    CreditApplicationRepository,
    InMemoryCreditApplicationRepository
}
import com.scalalazy.mainservice.module.PersonDB.{InMemoryPersonRepository, PersonRepository}
import com.scalalazy.mainservice.module.UserDB.{InMemoryUserRepository, UserRepository}
import com.scalalazy.mainservice.module.logger.{Logger => MyLogger}
import com.scalalazy.mainservice.route.{CreditApplicationResultRoutes, CreditApplicationRoute, UserRoute}
import com.typesafe.config.ConfigFactory
import eu.timepit.refined.auto._
import io.getquill.{PostgresJdbcContext, SnakeCase}
import sttp.apispec.openapi.circe.yaml.RichOpenAPI
import sttp.tapir.docs.openapi.OpenAPIDocsInterpreter
import zio._
import zio.interop.catz._
import io.circe.yaml.syntax._
import sttp.apispec.openapi.OpenAPI
import sttp.tapir.server.ziohttp.ZioHttpInterpreter
import sttp.tapir.swagger.SwaggerUI
import sttp.tapir.swagger.bundle.SwaggerInterpreter
import zhttp.http.{Http, Request, Response}
import zhttp.service.{ChannelFactory, EventLoopGroup}

import scala.jdk.CollectionConverters._
import scala.util.Try

object Main extends CatsApp {
    import cats.effect.unsafe.implicits.global
    type AppEnvironment = UserRepository
        with MyLogger with PersonRepository with CreditApplicationRepository with ChannelFactory
        with CreditApplicationRepository with EventLoopGroup

    private val userRoute = new UserRoute[AppEnvironment]
    private val creditApplicationRoute = new CreditApplicationRoute[AppEnvironment]
    private val creditApplicationResultRoute = new CreditApplicationResultRoutes[AppEnvironment]
//  private val personRoute = new PersonRoute[AppEnvironment]

    lazy val openAPIDoc: OpenAPI = OpenAPIDocsInterpreter()
        .toOpenAPI(
          userRoute.getEndPoints ++ creditApplicationRoute.getEndPoints ++ creditApplicationResultRoute.getEndpoints,
          "OpenScoring",
          "0.1.0")

    val swaggerFromOpenApi: Http[Any, Throwable, Request, Response] =
        ZioHttpInterpreter().toHttp(SwaggerUI[Task](openAPIDoc.toYaml))

    private val httpApp = userRoute.getRoutes ++
        creditApplicationRoute.getRoutes ++
        creditApplicationResultRoute.getRoutes ++
        swaggerFromOpenApi

//    override def run = for{
//        conf <- config
//        ctx <- context
//        result <- app.provide(
//            UserRepository.layer(ctx),
//            CreditApplicationRepository.layer(ctx),
//            PersonRepository.layer(ctx),
//            module.logger.Logger.layer
//        )
//    } yield result

    def run = app.provide(
      InMemoryUserRepository.layer,
      InMemoryCreditApplicationRepository.layer,
      InMemoryPersonRepository.layer,
      module.logger.Logger.layer,
      Scope.default,
      EventLoopGroup.default,
      ChannelFactory.auto
    )

    val config: ZIO[Any, Throwable, Application] = for {
        applicationConfigIO <- ZIO.fromTry(Try(Application.getConfig.load[cats.effect.IO]))
    } yield applicationConfigIO.unsafeRunSync() // TODO: заменить ciris на что-то ZIO-friendly

    val context: ZIO[Any, Throwable, PostgresJdbcContext[SnakeCase.type]] = for {
        conf <- config
        dbConf = ConfigFactory.parseMap(
          Map(
            "dataSourceClassName" -> conf.database.className.value,
            "dataSource.url" -> conf.database.url.value,
            "dataSource.user" -> conf.database.user.value,
            "dataSource.password" -> conf.database.password.value
          ).asJava)
    } yield new PostgresJdbcContext(SnakeCase, dbConf)

    val app: ZIO[AppEnvironment, Throwable, ExitCode] =
        for {
            conf <- TSConfigLoader.load()
            _ <- Console.printLine(s"Starting server on http://${conf.host.getHostAddress}:${conf.port}")
            _ <- zhttp.service.Server.start(conf.port, httpApp)
        } yield ExitCode.Success

}
