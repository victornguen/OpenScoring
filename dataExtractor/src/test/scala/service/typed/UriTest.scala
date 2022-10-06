package service.typed

import zio.Scope
import zio.test.{Spec, TestEnvironment, ZIOSpecDefault, assertTrue}

object UriTest extends ZIOSpecDefault {
    override def spec: Spec[TestEnvironment with Scope, Any] =
        suite("concat uris")(
            test("concat Uri with other Uri") {
                val doubleSlashedConcat = Uri("www.test.com/") / Uri("/method/param?q=1")
                assertTrue(doubleSlashedConcat == Uri("www.test.com/method/param?q=1"))
            },
            test("concat Uri with String") {
                val doubleSlashedConcat = Uri("www.test.com/") / "/method/param?q=1"
                assertTrue(doubleSlashedConcat == Uri("www.test.com/method/param?q=1"))
            }
        )

}
