package auth

import auth.Auth.{jwtDecode, jwtEncode}
import dto.{UserDTO, UserJwtDTO}
import service.services.{UserRepository, UserRepositoryTestImpl}
import zhttp.http.Middleware.bearerAuth
import zhttp.http._
import zio.ZIO
import zio.json.{DecoderOps, EncoderOps}

object Routes {

    val helloApp: Http[Any, Nothing, Request, Response] =
        Http.collect[Request] {
            case req @ Method.GET -> !! / "user" / "hello" =>
                val token = req.headerValue(HeaderNames.authorization).map(_.drop(7))
                val decoded = token.flatMap(t => jwtDecode(t))
                val json = decoded.map(_.content.fromJson[UserJwtDTO])
                json match {
                    case Some(value) => value match {
                        case Left(value) => Response.text(value)
                        case Right(value) => Response.text(s"Welcome to the party ${value.username}")
                    }
                    case None => Response.text(decoded.map(_.toJson).getOrElse(""))
                }
//                Response.text(s"Welcome to the ZIO party! $name")
        } @@ bearerAuth(jwtDecode(_).isDefined)

    val loginApp: Http[UserRepository, Throwable, Request, Response] = Http.collectZIO[Request] {
        case req @ Method.POST -> !! / "login" =>
            for {
                requestBody <- req.bodyAsString
                dto <- ZIO.fromEither(requestBody.fromJson[UserDTO]).mapError(e => new Throwable(e))
                userRepository <- UserRepository.authorizeUser(dto.username, dto.password)
                response        = if (userRepository.nonEmpty) Response.text(userRepository)
                                  else Response.text("Invalid username or password.")
                                      .setStatus(Status.Unauthorized)
            } yield response
    }

    val decodeJWT: Http[Any, Nothing, Request, Response] = Http.collect[Request] {
        case Method.GET -> !! / "decodejwt" / token =>
            val decoded = jwtDecode(token)
            Response.json(decoded.map(_.toJson).getOrElse(""))
    }

    val app = loginApp ++ helloApp ++ decodeJWT

}
