package helpers

import zhttp.http._
import zio.ZIO

import scala.util.Try

object RouteHelper {

    def logDebugMessageOnFailure[A, R, E](either:  Either[String, A], message: String = "Failed to parse request body")(f: A => ZIO[R, E, Response]): ZIO[R, E, Response] = either match {
        case Left(err) => ZIO.logDebug(s"$message $err").as(
            Response.text(err).setStatus(Status.BadRequest)
        )
        case Right(value) => f(value)
    }

}
