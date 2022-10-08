package service.services

import helpers.HttpHeadersHelper._
import helpers.RouteHelper.withMethod
import providers.ConfigProvider
import service.dto.openBanking._
import service.syntax.stringToUriType
import service.typed.Uri
import zhttp.http._
import zhttp.service.{ChannelFactory, Client, EventLoopGroup}
import zio._
import zio.json.DecoderOps
import helpers.TypeHelper._

import scala.util.Try

object AccountInfoService {

    def getAccountsFromBank(
        bankId: String,
        bearerToken: String,
        customerIpAddress: String,
        authDate: String,
        interactionId: String,
        additiveHeaders: Headers = Headers.empty
      ) =
        withMethod(bankId, "/accounts") { uri =>
            val headers = defaultHeaders(bearerToken, customerIpAddress, authDate, interactionId) ++ additiveHeaders
            for {
                res  <- Client.request(
                            uri.raw,
                            headers = headers,
                            method = Method.GET
                        )
                data <- res.body.asString
                _    <- ZIO.debug(data)
                dto  <- ZIO.fromEither(data.fromJson[AccountsDTO].toEitherThrowableA)
            } yield dto
        }

    def getBalancesFromBank(
        bankId: String,
        accountId: String,
        bearerToken: String,
        customerIpAddress: String,
        authDate: String,
        interactionId: String,
        additiveHeaders: Headers = Headers.empty
      ): ZIO[EventLoopGroup with ChannelFactory with ConfigProvider with UrlService, Throwable, BalancesDTO] =
        withMethod(bankId, "/accounts", accountId, "/balances") { uri =>
            val headers = defaultHeaders(bearerToken, customerIpAddress, authDate, interactionId) ++ additiveHeaders
            for {
                res  <- Client.request(
                            uri.raw,
                            method = Method.GET,
                            headers = headers
                        )
                data <- res.body.asString
                _    <- ZIO.debug(data)
                dto  <- ZIO.fromEither(data.fromJson[BalancesDTO].toEitherThrowableA)

            } yield dto
        }

}
