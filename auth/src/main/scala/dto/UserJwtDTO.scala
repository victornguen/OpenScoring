package dto

import zio.json.JsonCodec

case class UserJwtDTO (username:String, permissions: Seq[String])

object UserJwtDTO {
    implicit lazy val codec: JsonCodec[UserJwtDTO] = zio.json.DeriveJsonCodec.gen[UserJwtDTO]
}
