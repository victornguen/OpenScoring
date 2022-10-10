package service.models

import zio.json.JsonCodec


case class User (id: String, username: String, passwordHash: String, salt: String)

object User{
    implicit def codec: JsonCodec[User] = zio.json.DeriveJsonCodec.gen[User]
}