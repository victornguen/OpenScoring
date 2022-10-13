package com.scalalazy.mainservice.module.CreditApplicationDB
import com.scalalazy.mainservice.model.ExpectedFailure
import com.scalalazy.mainservice.model.database.{CreditApplication, Person}
import zio.{Ref, ZIO, ZLayer}

import scala.collection.mutable

final case class InMemoryCreditApplicationRepository(ref: Ref[Map[String, CreditApplication]])
    extends CreditApplicationRepository {

    override val repository: CreditApplicationRepository.Service = new CreditApplicationRepository.Service {
        def get(id: String): ZIO[Any, ExpectedFailure, Option[CreditApplication]] =
            for {
                creditApplication <- ref.get.map(_.get(id))
                u <- creditApplication match {
                    case Some(s) => ZIO.some(s)
                    case None => ZIO.none
                }
            } yield {
                u
            }

        def create(creditApplication: CreditApplication): ZIO[Any, ExpectedFailure, Unit] =
            ref.update(map => map + (creditApplication.id -> creditApplication)).unit

        def delete(id: String): ZIO[Any, ExpectedFailure, Unit] = ref.update(map => map - id).unit
    }

}

object InMemoryCreditApplicationRepository {
    def layer: ZLayer[Any, Nothing, InMemoryCreditApplicationRepository] = ZLayer.fromZIO(
      Ref.make(Map.empty[String, CreditApplication]).map(InMemoryCreditApplicationRepository.apply)
    )
}
