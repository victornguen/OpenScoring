package helpers

import helpers.CustomHeaders.X_FAPI
import zhttp.http.{Headers, Request}
import io.netty.handler.codec.http.{HttpHeaderNames, HttpHeaderValues}
import io.netty.util.AsciiString
import zio.ZIO


object HttpHeadersHelper {

    def defaultHeaders(bearerToken: String,
                          customerIpAddress: String,
                          authDate: String,
                          interactionId: String,
                          accept: AsciiString = HttpHeaderValues.APPLICATION_JSON): Headers =
        Headers(
            HttpHeaderNames.AUTHORIZATION -> s"Bearer $bearerToken",
            HttpHeaderNames.ACCEPT -> accept,
            X_FAPI.AUTH_DATE -> authDate,
            X_FAPI.CUSTOMER_IP_ADDRESS -> customerIpAddress,
            X_FAPI.INTERACTION_ID -> interactionId
        )

    def getHeaderOrFail(request: Request, headerName:String): ZIO[Any, Throwable, String] =
        ZIO.fromOption(request.headers.headerValue(headerName))
        .orElseFail(new Throwable(s"http header \"$headerName\" is required"))

}
