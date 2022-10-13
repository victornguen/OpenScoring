package auth

import auth.Auth.{jwtDecode, jwtEncode}
import zhttp.http.Middleware.bearerAuth
import zhttp.http._

object Routes {

  val helloApp: UHttpApp =
    Http.collect[Request] { case Method.GET -> !! / "user" / name / "greet" =>
      Response.text(s"Welcome to the ZIO party! $name")
    } @@ bearerAuth(jwtDecode(_).isDefined)

  val loginApp: UHttpApp = Http.collect[Request] {
    case Method.GET -> !! / "login" / user / password =>
      if (user.hashCode == password.reverse.hashCode) Response.text(jwtEncode(user))
      else Response.text("Invalid username or password.").setStatus(Status.Unauthorized)
  }

  val app: Http[Any, Nothing, Request, Response] =
    loginApp ++ helloApp

}
