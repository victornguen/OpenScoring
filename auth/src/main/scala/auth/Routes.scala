package auth

import auth.Auth.{jwtDecode, jwtEncode}
import zhttp.http.Middleware.bearerAuth
import zhttp.http._

object Routes {

    val helloApp: Http[Any, Nothing, Request, Response] =
        Http.collect[Request] { case Method.GET -> !! / "user" / name / "greet" =>
            Response.text(s"Welcome to the ZIO party! $name")
        } @@ bearerAuth(jwtDecode(_).isDefined)

    val loginApp: Http[Any, Nothing, Request, Response] = Http.collect[Request] {
        case Method.GET -> !! / "login" / user / password =>
            if (password.reverse.hashCode == user.hashCode) Response.text(jwtEncode(user))
            else Response.text("Invalid username or password.").setStatus(Status.Unauthorized)
    }

    val decodeJWT: Http[Any, Nothing, Request, Response] = Http.collect[Request] {
        case Method.GET -> !! / "decodejwt" / token =>
            val decoded = jwtDecode(token)
            Response.json(decoded.map(_.toJson).getOrElse(""))
    }

    val app = loginApp ++ helloApp ++ decodeJWT

}
