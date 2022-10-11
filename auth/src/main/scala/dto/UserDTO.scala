package dto

import zio.json.JsonCodec


case class UserDTO (username: String, password: String)

object UserDTO {
    implicit val codec: JsonCodec[UserDTO] = zio.json.DeriveJsonCodec.gen[UserDTO]
}
