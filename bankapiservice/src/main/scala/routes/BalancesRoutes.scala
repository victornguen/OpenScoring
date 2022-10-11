package routes

import helpers.CustomHeaders.X_FAPI
import helpers.HttpHeadersHelper.getHeaderOrFail
import helpers.JwtHelper.jwtDecode
import service.FakeBalancesDataService
import zhttp.http.Middleware.bearerAuth
import zhttp.http._
import zio.ZIO
import zio.json.EncoderOps

object BalancesRoutes {

    def getBalances: Http[Any, Throwable, Request, Response] =
        Http.collectZIO[Request] {
            case req @ Method.GET -> !! / "accounts" / accountId / "balances" =>
                for {
                    fakeData      <- FakeBalancesDataService.genBalancesDTO(accountId)
                    interactionId <- getHeaderOrFail(req, X_FAPI.INTERACTION_ID)
                    accounts       = Response.json(fakeData.toJson)

                } yield accounts.setHeaders(Headers(
                    X_FAPI.INTERACTION_ID -> interactionId,
                    HeaderNames.contentType -> HeaderValues.applicationJson
                ))
        } @@ bearerAuth(jwtDecode(_).isDefined)

    lazy val all = getBalances
}
