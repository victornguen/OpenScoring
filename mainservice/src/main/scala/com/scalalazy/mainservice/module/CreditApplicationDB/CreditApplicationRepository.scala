package com.scalalazy.mainservice.module.CreditApplicationDB

import com.scalalazy.mainservice.model.ExpectedFailure
import com.scalalazy.mainservice.model.database.CreditApplication
import io.getquill.context.jdbc.JdbcContext
import io.getquill.{PostgresJdbcContext, SnakeCase}
import zio.{ULayer, ZIO, ZLayer}

trait CreditApplicationRepository {

    val repository: CreditApplicationRepository.Service

}

object CreditApplicationRepository {
    def layer(ctx: PostgresJdbcContext[SnakeCase.type]): ULayer[LiveCreditApplicationRepository] =
        ZLayer.succeed(new LiveCreditApplicationRepository(ctx))

    trait Service {
        def get(id: String): ZIO[Any, ExpectedFailure, Option[CreditApplication]]

        def create(creditApplication: CreditApplication): ZIO[Any, ExpectedFailure, Unit]

        def delete(id: String): ZIO[Any, ExpectedFailure, Unit]
    }
}
