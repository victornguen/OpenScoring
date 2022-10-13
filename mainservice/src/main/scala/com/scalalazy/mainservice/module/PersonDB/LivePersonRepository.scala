package com.scalalazy.mainservice.module.PersonDB
import com.scalalazy.mainservice.model.database.Person
import com.scalalazy.mainservice.model.{DBFailure, ExpectedFailure}
import com.typesafe.config.Config
import io.getquill.context.jdbc.JdbcContext
import io.getquill.{PostgresJdbcContext, SnakeCase}
import zio.ZIO

class LivePersonRepository(ctx: PostgresJdbcContext[SnakeCase.type]) extends PersonRepository {
//    val config: Config
//    val ctx: PostgresJdbcContext[SnakeCase.type]
    import ctx._

    override val repository: PersonRepository.Service = new PersonRepository.Service {
        override def get(id: String): ZIO[Any, ExpectedFailure, Option[Person]] =
            for {
                list <- ZIO.from(ctx.run(query[Person].filter(_.id == lift(id)))).mapError(t => DBFailure(t))
                person <- list match {
                    case Nil => ZIO.none
                    case s :: _ => ZIO.some(s)
                }
            } yield person

        override def create(person: Person): ZIO[Any, ExpectedFailure, Unit] =
            ZIO
                .from(ctx.run(query[Person].insert(lift(person))))
                .mapError(t => DBFailure(t))
                .unit

        override def delete(id: String): ZIO[Any, ExpectedFailure, Unit] =
            ZIO
                .from(ctx.run(query[Person].filter(_.id == lift(id)).delete))
                .mapError(t => DBFailure(t))
                .unit
    }
}
