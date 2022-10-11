package helpers

import helpers.CustomHeaders.X_FAPI
import zhttp.http.Headers
import io.netty.handler.codec.http.{HttpHeaderNames, HttpHeaderValues}
import io.netty.util.AsciiString

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

}
