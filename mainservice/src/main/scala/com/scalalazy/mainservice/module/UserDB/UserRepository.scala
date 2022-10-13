package com.scalalazy.mainservice.module.UserDB

import com.scalalazy.mainservice.model.ExpectedFailure
import com.scalalazy.mainservice.model.database.User
import zio.{ULayer, ZIO, ZLayer}
import com.scalalazy.mainservice.module.UserDB.LiveUserRepository
import io.getquill.{PostgresJdbcContext, SnakeCase}

trait UserRepository {

    val repository: UserRepository.Service
}

object UserRepository {

    def layer(ctx: PostgresJdbcContext[SnakeCase.type]): ULayer[LiveUserRepository] = ZLayer.succeed(
      new LiveUserRepository(ctx)
    )

    trait Service {

        def get(id: Long): ZIO[Any, ExpectedFailure, Option[User]]

        def create(user: User): ZIO[Any, ExpectedFailure, Unit]

        def delete(id: Long): ZIO[Any, ExpectedFailure, Unit]
    }
}
