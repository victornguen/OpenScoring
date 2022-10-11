package helpers

import scala.util.{Failure, Success, Try}

object TypeHelper {

    implicit class EitherStringOps[A](e: Either[String, A]){
        def toEitherThrowableA: Either[Throwable, A] = e match {
            case Left(value) => Left(new Throwable(value))
            case Right(value) => Right(value)
        }
    }

    implicit class OptionOps[A](opt: Option[A]) {
        def toEitherThrowableA(message:String = ""): Either[Throwable, A] = opt match {
            case Some(value) => Right(value)
            case None => Left(new Throwable(message))
        }
    }

    implicit class TryOps[A](self: Try[A]){
        def toEitherThrowableA: Either[Throwable, A] = self match {
            case Failure(exception) => Left(exception)
            case Success(value) => Right(value)
        }
    }

}
