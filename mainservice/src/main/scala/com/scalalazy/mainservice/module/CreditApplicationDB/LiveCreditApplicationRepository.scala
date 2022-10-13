package com.scalalazy.mainservice.module.CreditApplicationDB
import com.scalalazy.mainservice.model.database.CreditApplication
import com.scalalazy.mainservice.model.{DBFailure, ExpectedFailure}
import com.typesafe.config.Config
import io.getquill.context.jdbc.JdbcContext
import io.getquill.{PostgresJdbcContext, SnakeCase}
import zio.ZIO

class LiveCreditApplicationRepository(ctx: PostgresJdbcContext[SnakeCase.type]) extends CreditApplicationRepository {
//    val config: Config
//    val ctx: PostgresJdbcContext[SnakeCase.type]
    import ctx._

    override val repository: CreditApplicationRepository.Service = new CreditApplicationRepository.Service {
        override def get(id: String): ZIO[Any, ExpectedFailure, Option[CreditApplication]] =
            for {
                list <- ZIO
                    .from(ctx.run(query[CreditApplication].filter(_.id == lift(id))))
                    .mapError(t => DBFailure(t))
                creditApplication <- list match {
                    case Nil => ZIO.none
                    case s :: _ => ZIO.some(s)
                }
            } yield creditApplication

        override def create(creditApplication: CreditApplication): ZIO[Any, ExpectedFailure, Unit] =
            ZIO
                .from(ctx.run(query[CreditApplication].insert(lift(creditApplication))))
                .mapError(t => DBFailure(t))
                .unit

        override def delete(id: String): ZIO[Any, ExpectedFailure, Unit] =
            ZIO
                .from(ctx.run(query[CreditApplication].filter(_.id == lift(id)).delete))
                .mapError(t => DBFailure(t))
                .unit
    }

}
