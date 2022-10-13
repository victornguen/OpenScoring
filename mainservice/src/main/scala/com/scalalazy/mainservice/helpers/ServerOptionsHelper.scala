package com.scalalazy.mainservice.helpers
import com.scalalazy.mainservice.model.response.BadRequestResponse
import io.circe.generic.auto._
import sttp.tapir.DecodeResult.Error
import sttp.tapir.json.circe._
import sttp.tapir.server.http4s.Http4sServerOptions
import zio.RIO
import zio.interop.catz._

trait ServerOptionsHelper[R] {

//    implicit val customServerOptions: Http4sServerOptions[RIO[R, *]] = Http4sServerOptions
//        .default[RIO[R, *]]
//        .copy(
//          decodeFailureHandler = (request, input, failure) => {
//              failure match {
//                  case Error(_, error) =>
//                      DecodeFailureHandling.response(jsonBody[BadRequestResponse])(BadRequestResponse(error.toString))
//                  case _ => ServerDefaults.decodeFailureHandler(request, input, failure)
//              }
//          }
//        )
}
