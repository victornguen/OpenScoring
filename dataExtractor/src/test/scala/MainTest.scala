import zio.Scope
import zio.test.{Spec, TestEnvironment, ZIOSpecDefault}

object MainTest extends ZIOSpecDefault {
  override def spec: Spec[TestEnvironment with Scope, Any] = ???
}
