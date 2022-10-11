package helpers

import providers.ConfigProvider
import service.dto.GetBalancesDTO
import service.services.UrlService
import service.typed.Uri
import zhttp.http._
import zio.ZIO
import helpers.TypeHelper._

import scala.util.Try

object OpenApiRouteHelper {

    def withMethod[R, A](bankId: String, route: String)(f: Uri => ZIO[R,Throwable,A]): ZIO[R with ConfigProvider with UrlService, Throwable, A] = {
        val target = for {
            urls   <- UrlService.getDefaultUrls
            baseurl = urls.getOrElse(bankId, "")
            target  <- ZIO.succeed(Uri(baseurl) / route)

        } yield target
        target.flatMap(f)
    }

    def withMethod[R, A](bankId: String, routeParts: String *)(f: Uri => ZIO[R, Throwable, A]): ZIO[R with ConfigProvider with UrlService, Throwable, A] = {
        val target = for {
            urls <- UrlService.getDefaultUrls
            baseurl <- ZIO.fromTry(Try(urls(bankId)))
            target = routeParts.foldLeft(Uri(baseurl))(_ / _)
            _ <- zio.Console.printLine(target)
        } yield target
        target.flatMap(f)
    }

    def logDebugMessageOnFailure[A, R, E](either:  Either[String, A], message: String = "Failed to parse request body")(f: A => ZIO[R, E, Response]): ZIO[R, E, Response] = either match {
        case Left(err) => ZIO.logError(s"$message $err").as(
            Response.text(err).setStatus(Status.BadRequest)
        )
        case Right(value) => f(value)
    }

}
