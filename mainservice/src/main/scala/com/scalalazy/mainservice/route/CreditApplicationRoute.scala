package com.scalalazy.mainservice.route
import com.scalalazy.mainservice.helpers.{RouteHelper, ServerOptionsHelper}
import com.scalalazy.mainservice.model.database.{CreditApplication, Person}
import com.scalalazy.mainservice.model.dto.CreateCreditApplicationDTO
import com.scalalazy.mainservice.model.dto.response.CreditApplicationCreatedDTO
import com.scalalazy.mainservice.model.response.{ErrorResponse, InternalServerErrorResponse, NotFoundResponse}
import com.scalalazy.mainservice.model.{ExpectedFailure, NotFoundFailure}
import com.scalalazy.mainservice.module.CreditApplicationDB.{create, delete, CreditApplicationRepository}
import com.scalalazy.mainservice.module.PersonDB.PersonRepository
import com.scalalazy.mainservice.module.logger.{debug, Logger}
import com.scalalazy.mainservice.module.{CreditApplicationDB, PersonDB}
import io.circe.generic.auto._
import org.http4s.dsl.Http4sDsl
import sttp.model.StatusCode
import sttp.tapir.generic.auto._
import sttp.tapir.json.circe.jsonBody
import sttp.tapir.server.ziohttp.ZioHttpInterpreter
import sttp.tapir.ztapir._
import zhttp.http.{Http, Request, Response}
import zio.{RIO, ZIO}

import java.text.SimpleDateFormat
import java.util.UUID

class CreditApplicationRoute[R <: CreditApplicationRepository with PersonRepository with Logger]
    extends Http4sDsl[RIO[R, *]] with RouteHelper[R] with ServerOptionsHelper[R] {

    private val formatter: SimpleDateFormat = new SimpleDateFormat("dd.MM.yyyy")

    private val getCreditApplicationEndPoint = endpoint.get
        .description("Get credit application by id")
        .in("creditapplication" / path[String]("credit application id"))
        .errorOut(oneOf(
          oneOfVariant(statusCode(StatusCode.InternalServerError) and jsonBody[InternalServerErrorResponse].description(
            "Internal server error")),
          oneOfVariant(statusCode(StatusCode.NotFound) and jsonBody[NotFoundResponse].description("Not Found"))
        ))
        .out(jsonBody[CreditApplication])

    private val createCreditApplicationEndPoint = endpoint.post
        .description("Create credit application")
        .in("creditapplication")
        .in(jsonBody[CreateCreditApplicationDTO])
        .errorOut(
          oneOf[ErrorResponse](
            oneOfVariant(statusCode(StatusCode.InternalServerError) and jsonBody[InternalServerErrorResponse]
                .description("Internal server error")))
        )
        .out(jsonBody[CreditApplicationCreatedDTO])

    private val deleteCreditApplicationEndPoint = endpoint.delete
        .description("Delete credit application")
        .in("creditapplication" / path[String]("credit application id"))
        .errorOut(oneOf(
          oneOfVariant(statusCode(StatusCode.InternalServerError) and jsonBody[InternalServerErrorResponse].description(
            "Internal server error")),
          oneOfVariant(statusCode(StatusCode.NotFound) and jsonBody[NotFoundResponse].description("Not Found"))
        ))
        .out(emptyOutput)

    val getRoutes: Http[R, Throwable, Request, Response] = ZioHttpInterpreter().toHttp(
      List(
        getCreditApplicationEndPoint.zServerLogic(applicationId => handleError(getCreditApplication(applicationId))),
        createCreditApplicationEndPoint.zServerLogic { application =>
            val newPersonId = UUID.randomUUID.toString
            val newApplicationId = UUID.randomUUID.toString
            handleError(
              PersonDB.create(
                Person(
                  newPersonId,
                  application.firstName,
                  application.middleName,
                  application.lastName,
                  application.mobileNumber,
                  application.email,
                  formatter.parse(application.dateOfBirth) // dd.MM.yyyy
                )
              ) &> create(
                CreditApplication(
                  newApplicationId,
                  application.sum,
                  application.currency,
                  application.periodInMonth,
                  newPersonId)
              ) &> ZIO.succeed(CreditApplicationCreatedDTO(newApplicationId, newPersonId))
            )
        },
        deleteCreditApplicationEndPoint.zServerLogic { id =>
            val result = for {
                _ <- debug(s"id: $id")
                user <- getCreditApplication(id)
                _ <- delete(user.id)
            } yield {}

            handleError(result)
        }
      )
    )

    val getEndPoints = {
        List(getCreditApplicationEndPoint, createCreditApplicationEndPoint, deleteCreditApplicationEndPoint)
    }

    private def getCreditApplication(applicationId: String): ZIO[R, ExpectedFailure, CreditApplication] = {
        for {
            _ <- debug(s"id: $applicationId")
            creditApplication <- CreditApplicationDB.get(applicationId)
            application <- creditApplication match {
                case None => ZIO.fail(NotFoundFailure(s"Can not find a credit application by $applicationId"))
                case Some(s) => ZIO.succeed(s)
            }
        } yield application
    }
}
