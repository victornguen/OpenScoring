package com.scalalazy.mainservice.route
import com.scalalazy.mainservice.helpers.{RouteHelper, ServerOptionsHelper}
import com.scalalazy.mainservice.model.{ConfigError, ExpectedFailure, NotFoundFailure}
import com.scalalazy.mainservice.model.dto.{
    CreateCreditApplicationDTO,
    CreateCreditRequestDTO,
    CreditApplicationResultDTO,
    Month
}
import com.scalalazy.mainservice.model.dto.response.CreditApplicationCreatedDTO
import com.scalalazy.mainservice.model.response.{ErrorResponse, InternalServerErrorResponse, NotFoundResponse}
import com.scalalazy.mainservice.module.CreditApplicationDB
import com.scalalazy.mainservice.module.CreditApplicationDB.CreditApplicationRepository
import com.scalalazy.mainservice.module.PersonDB.PersonRepository
import com.scalalazy.mainservice.module.logger.{debug, Logger}
import com.typesafe.config.ConfigFactory
import org.http4s.dsl.Http4sDsl
import sttp.model.StatusCode
import sttp.tapir.ztapir.{endpoint, oneOf, oneOfVariant, statusCode, _}
import zio.RIO
import sttp.tapir.generic.auto._
import io.circe.generic.auto._
import sttp.tapir.json.circe.jsonBody
import sttp.tapir.server.ziohttp.ZioHttpInterpreter
import zhttp.http.{Http, HttpData, Method, Request, Response}
import zhttp.service.{ChannelFactory, Client, EventLoopGroup}
import zio.json.{DecoderOps, EncoderOps}
import zio.{RIO, ZIO}

class CreditApplicationResultRoutes[
    R <: Logger with EventLoopGroup with ChannelFactory with CreditApplicationRepository]
    extends Http4sDsl[RIO[R, *]] with RouteHelper[R] with ServerOptionsHelper[R] {

    private val getCreditApplicationResultEndPoint = endpoint.get
        .description("Get credit application result")
        .in("creditapplication" / "result" / path[String].description("credit application ID"))
        .errorOut(
          oneOf[ErrorResponse](
            oneOfVariant(
              statusCode(StatusCode.InternalServerError) and jsonBody[InternalServerErrorResponse]
                  .description("Internal server error")),
            oneOfVariant(statusCode(StatusCode.NotFound) and jsonBody[NotFoundResponse].description("Not Found"))
          )
        )
        .out(jsonBody[List[CreditApplicationResultDTO]])

    val getRoutes = ZioHttpInterpreter().toHttp(
      List(
        getCreditApplicationResultEndPoint.zServerLogic { applicationId =>
            handleError(getResultForCreditApplication(applicationId))
        }
      ))

    def getResultForCreditApplication(applicationId: String) =
        for {
            creditApplication <- CreditApplicationDB.get(applicationId)
            hostConf <- ZIO
                .from(ConfigFactory.load.getString("services.recommendationService"))
                .mapError(e => ConfigError(e.toString))
            //              ca <- ZIO.fromOption(creditApplication).mapError(e => NotFoundFailure(s"not found application : $applicationId"))
            request = CreateCreditRequestDTO(
              amount = BigDecimal(creditApplication.map(_.sum).getOrElse(10000L)),
              timeLimitMonth = Month(creditApplication.map(_.periodInMonth.toDouble).getOrElse(3.0)),
              accountBalance = BigDecimal(1000),
              monthIncome = BigDecimal(100)
            )
            response <- Client
                .request(
                  "http://openscoring-recomendation-system:8999/decision",
                  Method.POST,
                  content = HttpData.fromCharSequence(request.toJson))
                .mapError(e => NotFoundFailure(""))

            json <- response.bodyAsString
                .map(_.fromJson[List[CreditApplicationResultDTO]])
                .mapError(e => NotFoundFailure(s"can't parse response"))
            result <- json match {
                case Left(value) => ZIO.fail(NotFoundFailure(s"can't parse response: $value"))
                case Right(value) => ZIO.succeed(value)
            }
        } yield result

    def getEndpoints = List(getCreditApplicationResultEndPoint)
}
