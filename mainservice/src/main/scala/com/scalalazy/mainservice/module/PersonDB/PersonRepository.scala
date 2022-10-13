package com.scalalazy.mainservice.module.PersonDB

import com.scalalazy.mainservice.model.ExpectedFailure
import com.scalalazy.mainservice.model.database.Person
import io.getquill.{PostgresJdbcContext, SnakeCase}
import io.getquill.context.jdbc.JdbcContext
import zio.{ULayer, ZIO, ZLayer}

trait PersonRepository {
    val repository: PersonRepository.Service
}

object PersonRepository {

    def layer(ctx: PostgresJdbcContext[SnakeCase.type]): ULayer[LivePersonRepository] =
        ZLayer.succeed(new LivePersonRepository(ctx))

    trait Service {
        def get(id: String): ZIO[Any, ExpectedFailure, Option[Person]]

        def create(person: Person): ZIO[Any, ExpectedFailure, Unit]

        def delete(id: String): ZIO[Any, ExpectedFailure, Unit]
    }
}
