package com.scalalazy.mainservice.module.UserDB

import com.scalalazy.mainservice.model.database.User
import com.scalalazy.mainservice.model.{DBFailure, ExpectedFailure}
import com.typesafe.config.Config
import io.getquill.{PostgresJdbcContext, SnakeCase}
import zio._

class LiveUserRepository(ctx: PostgresJdbcContext[SnakeCase.type]) extends UserRepository {
//    val config: Config
//    val ctx: PostgresJdbcContext[SnakeCase.type]
    import ctx._

    override val repository: UserRepository.Service = new UserRepository.Service {

        def get(id: Long): ZIO[Any, ExpectedFailure, Option[User]] =
            for {
                list <- ZIO.from(ctx.run(query[User].filter(_.id == lift(id)))).mapError(t => DBFailure(t))
                user <- list match {
                    case Nil => ZIO.none
                    case s :: _ => ZIO.some(s)
                }
            } yield {
                user
            }

        def create(user: User): ZIO[Any, ExpectedFailure, Unit] =
            ZIO
                .from(ctx.run(query[User].insert(lift(user))))
                .mapError(t => DBFailure(t))
                .unit

        def delete(id: Long): ZIO[Any, ExpectedFailure, Unit] =
            ZIO
                .from(ctx.run(query[User].filter(_.id == lift(id)).delete))
                .mapError(t => DBFailure(t))
                .unit
    }
}

object LiveUserRepository {
    def layer(ctx: PostgresJdbcContext[SnakeCase.type]): ULayer[LiveUserRepository] = ZLayer.succeed(
      new LiveUserRepository(ctx)
    )
}
