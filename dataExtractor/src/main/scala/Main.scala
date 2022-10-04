import zio._
import zio.Console.printLine

object Main extends ZIOAppDefault {
  override def run: ZIO[Environment with ZIOAppArgs with Scope, Any, Any] =
    printLine("Welcome to your first ZIO app!")
}