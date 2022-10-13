package com.scalalazy.mainservice.helpers
import com.scalalazy.mainservice.implicits.Throwable.ThrowableExtension
import com.scalalazy.mainservice.model.{DBFailure, ExpectedFailure, NotFoundFailure}
import com.scalalazy.mainservice.model.response.{ErrorResponse, InternalServerErrorResponse, NotFoundResponse}
import zio.ZIO

trait RouteHelper[R] {

    def handleError[A](result: ZIO[R, ExpectedFailure, A]): ZIO[R, ErrorResponse, A] = {
        result.mapError {
            case DBFailure(t) =>
                InternalServerErrorResponse("Database FAILURE !!!", t.getMessage, t.getStacktrace)
            case NotFoundFailure(message) => NotFoundResponse(message)

        }

    }
}
