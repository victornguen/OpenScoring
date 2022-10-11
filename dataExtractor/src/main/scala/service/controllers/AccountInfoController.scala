package service.controllers

import helpers.OpenBankingApiHelper.generateInteractionId
import helpers.OpenApiRouteHelper
import helpers.OpenApiRouteHelper.logDebugMessageOnFailure
import providers.ConfigProvider
import service.dto.{GetAccountsDTO, GetBalancesDTO}
import service.services.{AccountInfoService, UrlService}
import zhttp.http._
import zhttp.service.{ChannelFactory, EventLoopGroup}
import zio.ZIO
import zio.json.{DecoderOps, EncoderOps}

object AccountInfoController {

    def getAccounts: Http[EventLoopGroup with ChannelFactory with ConfigProvider with UrlService, Throwable, Request, Response] =
        Http.collectZIO[Request] {
            case req @ Method.POST -> !! / "accounts" =>
                for {
                    jsonBody <- req.body.asString.map(_.fromJson[GetAccountsDTO])
                    accounts <- logDebugMessageOnFailure(jsonBody) { dto =>
                                    AccountInfoService.getAccountsFromBank(bankId = dto.bankId,
                                                                           bearerToken = dto.bearerToken,
                                                                           customerIpAddress = dto.customerIpAddress,
                                                                           authDate = dto.authDate,
                                                                           interactionId = generateInteractionId
                                    ).map(account => Response.json(account.toJson))
                                }

                } yield accounts

        }

    def getBalances: Http[EventLoopGroup with ChannelFactory with ConfigProvider with UrlService, Throwable, Request, Response] =
        Http.collectZIO[Request] {
            case req @ Method.POST -> !! / "balances" =>
                for {
                    jsonBody <- req.body.asString.map(_.fromJson[GetBalancesDTO])
                    balances <- logDebugMessageOnFailure(jsonBody) {
                                    dto =>
                                        AccountInfoService.getBalancesFromBank(
                                            bankId = dto.bankId,
                                            bearerToken = dto.bearerToken,
                                            accountId = dto.accountId,
                                            customerIpAddress = dto.customerIpAddress,
                                            authDate = dto.authDate,
                                            interactionId = generateInteractionId
                                        ).map(balance => Response.json(balance.toJson))
                                }
                } yield balances
        }

    lazy val routes: Http[EventLoopGroup with ChannelFactory with ConfigProvider with UrlService, Throwable, Request, Response] = getBalances ++ getAccounts

}
