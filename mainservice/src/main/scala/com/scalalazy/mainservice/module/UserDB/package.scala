package com.scalalazy.mainservice.module

import com.scalalazy.mainservice.model.ExpectedFailure
import com.scalalazy.mainservice.model.database.User
import zio.ZIO

package object UserDB {

    def get(id: Long): ZIO[UserRepository, ExpectedFailure, Option[User]] =
        ZIO.serviceWithZIO(_.repository.get(id))

    def create(user: User): ZIO[UserRepository, ExpectedFailure, Unit] =
        ZIO.serviceWithZIO(_.repository.create(user))

    def delete(id: Long): ZIO[UserRepository, ExpectedFailure, Unit] =
        ZIO.serviceWithZIO(_.repository.delete(id))
}
