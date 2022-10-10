package auth

import auth.Auth.{jwtDecode, jwtEncode}
import dto.UserDTO
import service.services.{UserRepository, UserRepositoryTestImpl}
import zhttp.http.Middleware.bearerAuth
import zhttp.http._
import zio.ZIO
import zio.json.DecoderOps

object Routes {

    val helloApp: Http[Any, Nothing, Request, Response] =
        Http.collect[Request] { case Method.GET -> !! / "user" / name / "hello" =>
            Response.text(s"Welcome to the ZIO party! $name")
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
