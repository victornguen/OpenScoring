package service.controllers.mock

import helpers.RouteHelper.sendDebugMessageOnFailure
import providers.ConfigProvider
import service.dto.openBanking._
import service.dto._
import service.services.{AccountInfoService, UrlService}
import zhttp.http._
import zhttp.service.{ChannelFactory, EventLoopGroup}
import zio.ZIO
import zio.json.{DecoderOps, EncoderOps, JsonCodec, JsonDecoder}

object AccountInfoControllerMock {

    def getAccounts =
        sendMockData[AccountsDTO]("accounts", Method.POST)(mockdata.accounts)

    def getBalances =
        sendMockData[BalancesDTO]("balances", Method.POST)(mockdata.balances)

    private def sendMockData[DtoType](route: String, method: Method)(json: String)(implicit codec: JsonCodec[DtoType]): Http[Any, Throwable, Request, Response] =
        Http.collectZIO[Request] {
            case method -> !! / "mock" / `route` =>
                val jsonBody = json.fromJson[DtoType]
                for {
                    balances <- sendDebugMessageOnFailure(jsonBody) {
                                    json => ZIO.succeed(Response.json(json.toJson))
                                }
                } yield balances
        }

    val routes = getAccounts ++ getBalances

}
