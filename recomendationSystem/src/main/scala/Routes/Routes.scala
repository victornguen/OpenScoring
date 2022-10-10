package Routes

import creditRates.data.CreditRate
import creditRates.storage.CreditRatesStorage
import creditRequests.data.CreditRequest
import creditRequests.storage.CreditRequestStorage
import creditRules.data.CreditRule
import creditRules.storage.CreditRulesStorage
import zhttp.http._
import zio.ZIO
import zio.json.{DecoderOps, EncoderOps}

object Routes {

    def rateRoutes: Http[CreditRatesStorage, Throwable, Request, Response] = Http.collectZIO[Request] {
        case Method.GET -> !! / "rates"        => CreditRatesStorage.getRates.map(r => Response.json(r.toJson))
        case req @ Method.POST -> !! / "rates" => for {
                body <- req.bodyAsString.map(_.fromJson[CreditRate])
                r    <- body match {
                            case Left(value)  => ZIO
                                    .log(s"Failed to parse the input: $value")
                                    .as(
                                        Response.text(value).setStatus(Status.BadRequest)
                                    )
                            case Right(value) => CreditRatesStorage.addRate(value).map(id => Response.text(id))
                        }
            } yield r
    }

    def creditRequestRoutes: Http[CreditRequestStorage, Throwable, Request, Response] = Http.collectZIO[Request] {
        case Method.GET -> !! / "requests"        => CreditRequestStorage.getRequests.map(r => Response.json(r.toJson))
        case req @ Method.POST -> !! / "requests" => for {
                body <- req.bodyAsString.map(_.fromJson[CreditRequest])
                r    <- body match {
                            case Left(value)  => ZIO
                                    .log(s"Failed to parse the input: $value")
                                    .as(
                                        Response.text(value).setStatus(Status.BadRequest)
                                    )
                            case Right(value) => CreditRequestStorage.addRequest(value).map(id => Response.text(id))
                        }
            } yield r
    }

    def rulesRoutes: Http[CreditRulesStorage, Throwable, Request, Response] = Http.collectZIO[Request] {
        case Method.GET -> !! / "rules"        => CreditRulesStorage.getRules.map(r => Response.json(r.toJson))
        case req @ Method.POST -> !! / "rules" => for {
                body <- req.bodyAsString.map(_.fromJson[CreditRule])
                r    <- body match {
                            case Left(value)  => ZIO
                                    .log(s"Failed to parse the input: $value")
                                    .as(
                                        Response.text(value).setStatus(Status.BadRequest)
                                    )
                            case Right(value) => CreditRulesStorage.addRule(value).map(id => Response.text(id))
                        }
            } yield r
    }

}
