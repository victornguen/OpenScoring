package com.scalalazy.mainservice.module.logger

import com.scalalazy.mainservice.implicits.Throwable._
import zio._
import zio.Console

import java.io.IOException

class ConsoleLogger extends Logger {

    val logger: Logger.Service = new Logger.Service {

        def error(message: => String): ZIO[Any, Nothing, Unit] = ZIO.logError(message)

        def warn(message: => String): ZIO[Any, Nothing, Unit] = ZIO.logWarning(message)

        def info(message: => String): ZIO[Any, Nothing, Unit] = ZIO.logInfo(message)

        def debug(message: => String): ZIO[Any, Nothing, Unit] = ZIO.logDebug(message)

        def trace(message: => String): ZIO[Any, Nothing, Unit] = ZIO.logTrace(message)

        def error(t: Throwable)(message: => String): ZIO[Any, Nothing, Unit] =
            ZIO.logError(s"message: $message, exception: ${t.getStacktrace}")

        def warn(t: Throwable)(message: => String): ZIO[Any, Nothing, Unit] =
            ZIO.logWarning(s"message: $message, exception: ${t.getStacktrace}")

        def info(t: Throwable)(message: => String): ZIO[Any, Nothing, Unit] =
            ZIO.logInfo(s"message: $message, exception: ${t.getStacktrace}")

        def debug(t: Throwable)(message: => String): ZIO[Any, Nothing, Unit] =
            ZIO.logDebug(s"message: $message, exception: ${t.getStacktrace}")

        def trace(t: Throwable)(message: => String): ZIO[Any, Nothing, Unit] =
            ZIO.logTrace(s"message: $message, exception: ${t.getStacktrace}")

    }
}
