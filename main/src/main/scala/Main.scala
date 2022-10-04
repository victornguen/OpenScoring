import zio._
import zio.Console.printLine

object Main extends ZIOAppDefault  {
  def run =
    printLine("Welcome to your first ZIO app!").exitCode
}