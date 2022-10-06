package service

import helpers.CustomHeaders.X_FAPI
import helpers.RouteHelper.withMethod
import io.netty.handler.codec.http.{HttpHeaderNames, HttpHeaderValues}
import providers.ConfigProvider
import service.dto.GetAccountsDTO
import service.dto.openBanking.AccountsDTO
import service.typed.Uri
import zhttp.http._
import zhttp.service.{ChannelFactory, Client, EventLoopGroup}
import zio._
import zio.json.{DecoderOps, EncoderOps}
import zio.stream.ZStream
import zhttp.http._
import zio._
import zio.json._

trait AccountInfoService {
    def getAccounts()
}

object AccountInfoService {

    def getAccounts(): Http[EventLoopGroup with ChannelFactory with ConfigProvider with UrlService, Any, Request, Response] =
        Http.collectZIO[Request] {
            case req @ (Method.POST -> !! / "accounts") =>
                for {
                    dto      <- req.body.asString.map(_.fromJson[GetAccountsDTO])
                    accounts <- dto match {
                                    case Left(err)  => ZIO.debug(s"Failed to parse request body $err").as(
                                            Response.text(err).setStatus(Status.BadRequest)
                                        )
                                    case Right(dto) => getAccountsFromBank(bankId = dto.bankId,
                                                                           bearerToken = dto.bearerToken,
                                                                           dto.customerIpAddress,
                                                                           dto.authDate,
                                                                           dto.interactionId
                                        ).map(dto => Response.json(dto.toJson))
                                }

                } yield accounts

        }

    def getAccountsFromBank(
        bankId: String,
        bearerToken: String,
        customerIpAddress: String,
        authDate: String,
        interactionId: String
      ): ZIO[EventLoopGroup with ChannelFactory with ConfigProvider with UrlService, Any, AccountsDTO] = withMethod(bankId, "/accounts") { uri =>
        val headers = Headers(
            HttpHeaderNames.AUTHORIZATION -> s"Bearer $bearerToken",
            HttpHeaderNames.ACCEPT        -> HttpHeaderValues.APPLICATION_JSON,
            X_FAPI.AUTH_DATE              -> authDate,
            X_FAPI.CUSTOMER_IP_ADDRESS    -> customerIpAddress,
            X_FAPI.INTERACTION_ID         -> interactionId
        )
        for {
            res  <- Client.request(
                        uri.raw,
                        headers = headers,
                        method = Method.GET
                    )
            data <- res.body.asString
            _    <- Console.printLine(data)
            dto  <- ZIO.fromEither(data.fromJson[AccountsDTO])
        } yield dto
    }

}
