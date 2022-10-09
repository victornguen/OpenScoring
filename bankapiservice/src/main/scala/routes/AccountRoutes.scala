package routes

import helpers.CustomHeaders._
import helpers.HttpHeadersHelper.getHeaderOrFail
import helpers.JwtHelper.jwtDecode
import service._
import zhttp.http.Middleware.bearerAuth
import zhttp.http._
import zio.ZIO
import zio.json._

object AccountRoutes {

    def getAccounts: Http[Any, Throwable, Request, Response] =
        Http.collectZIO[Request] {
            case req @ Method.GET -> !! / "accounts" =>
                for {
                    fakeData      <- FakeAccountsDataService.genAccountsDTO
                    interactionId <- getHeaderOrFail(req, X_FAPI.INTERACTION_ID)
                    accounts       = Response.json(fakeData.toJson)

                } yield accounts.setHeaders(Headers(
                    X_FAPI.INTERACTION_ID -> interactionId,
                    HeaderNames.contentType -> HeaderValues.applicationJson
                ))
        } @@ bearerAuth(jwtDecode(_).isDefined)

    lazy val all = getAccounts

}
