package com.scalalazy.mainservice.implicits

import cats.effect.Sync
import cats.effect.kernel.Concurrent
import io.circe.{Decoder, Encoder}
import org.http4s.circe.{jsonEncoderOf, jsonOf}
import org.http4s.{EntityDecoder, EntityEncoder}

object Circe {
  implicit def circeJsonDecoder[F[_]: Concurrent, A](implicit decoder: Decoder[A]): EntityDecoder[F, A] =
    jsonOf[F, A]

  implicit def circeJsonEncoder[F[_]: Sync, A](implicit encoder: Encoder[A]): EntityEncoder[F, A] =
    jsonEncoderOf[F, A]
}
