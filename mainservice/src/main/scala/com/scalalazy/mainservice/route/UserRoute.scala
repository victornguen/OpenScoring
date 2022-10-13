package com.scalalazy.mainservice.route

import com.scalalazy.mainservice.helpers.RouteHelper
import com.scalalazy.mainservice.model.database.User
import com.scalalazy.mainservice.model.dto.CreateUserDTO
import com.scalalazy.mainservice.model.response.{ErrorResponse, InternalServerErrorResponse, NotFoundResponse}
import com.scalalazy.mainservice.model.{ExpectedFailure, NotFoundFailure}
import com.scalalazy.mainservice.module.UserDB._
import com.scalalazy.mainservice.module.logger._
import io.circe.generic.auto._
import org.http4s.dsl.Http4sDsl
import sttp.model.StatusCode
import sttp.tapir.generic.auto._
import sttp.tapir.json.circe.jsonBody
import sttp.tapir.server.ziohttp.ZioHttpInterpreter
import sttp.tapir.ztapir._
import zhttp.http.{Http, Request, Response}
import zio.{RIO, ZIO}

import java.util.UUID

class UserRoute[R <: UserRepository with Logger] extends Http4sDsl[RIO[R, *]] with RouteHelper[R] {
//    private implicit val customServerOptions: Http4sServerOptions[RIO[R, *]] = Http4sServerOptions
//        .default[RIO[R, *]]
//        .copy(
//          decodeFailureHandler = (request, input, failure) => {
//              failure match {
//                  case Error(_, error) =>
//                      DecodeFailureHandling.response(jsonBody[BadRequestResponse])(BadRequestResponse(error.toString))
//                  case _ => ServerDefaults.decodeFailureHandler(request, input, failure)
//              }
//          }
//        )

    private val getUserEndPoint = endpoint.get
        .in("user" / path[Long]("user id"))
        .errorOut(oneOf(
          oneOfVariant(statusCode(StatusCode.InternalServerError) and jsonBody[InternalServerErrorResponse].description(
            "Internal server error")),
          oneOfVariant(statusCode(StatusCode.NotFound) and jsonBody[NotFoundResponse].description("Not Found"))
        ))
        .out(jsonBody[User])

    private val createUserEndPoint = endpoint.post
        .in("user")
        .in(jsonBody[CreateUserDTO])
        .errorOut(
          oneOf[ErrorResponse](
            oneOfVariant(statusCode(StatusCode.InternalServerError) and jsonBody[InternalServerErrorResponse]
                .description("Internal server error"))
          ))
        .out(statusCode(StatusCode.Created))

    private val deleteUserEndPoint = endpoint.delete
        .in("user" / path[Long]("user id"))
        .errorOut(oneOf(
          oneOfVariant(statusCode(StatusCode.InternalServerError) and jsonBody[InternalServerErrorResponse].description(
            "Internal server error")),
          oneOfVariant(statusCode(StatusCode.NotFound) and jsonBody[NotFoundResponse].description("Not Found"))
        ))
        .out(emptyOutput)

    val getRoutes: Http[R, Throwable, Request, Response] = ZioHttpInterpreter().toHttp(
      List(
        getUserEndPoint.zServerLogic { userId =>
            handleError(getUser(userId))
        },
        createUserEndPoint.zServerLogic { user =>
            handleError(create(User(scala.util.Random.nextInt, user.name, user.age)))
        },
        deleteUserEndPoint.zServerLogic { id =>
            val result = for {
                _ <- debug(s"id: $id")
                user <- getUser(id)
                _ <- delete(user.id)
            } yield {}

            handleError(result)
        }
      ))

    val getEndPoints = {
        List(getUserEndPoint, createUserEndPoint, deleteUserEndPoint)
    }

    private def getUser(userId: Long): ZIO[R, ExpectedFailure, User] = {
        for {
            _ <- debug(s"id: $userId")
            user <- get(userId)
            u <- user match {
                case None => ZIO.fail(NotFoundFailure(s"Can not find a user by $userId"))
                case Some(s) => ZIO.succeed(s)
            }
        } yield {
            u
        }
    }

}
