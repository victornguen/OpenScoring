package helpers

import providers.ConfigProvider
import service.UrlService
import service.typed.Uri
import zio.ZIO

object RouteHelper {

    def withMethod[R, E, A](bankId: String, route: String)(f: Uri => ZIO[R,E,A]): ZIO[R with ConfigProvider with UrlService, Any, A] = {
        for {
            urls   <- UrlService.getDefaultUrls
            target  = Uri(urls(bankId)) / route
            result <- f(target)
        } yield result

    }

}
