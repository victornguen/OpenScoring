package com.scalalazy.mainservice.module.PersonDB
import com.scalalazy.mainservice.model.ExpectedFailure
import com.scalalazy.mainservice.model.database.Person
import zio.{Ref, ZIO, ZLayer}

import scala.collection.mutable

final case class InMemoryPersonRepository(ref: Ref[mutable.Map[String, Person]]) extends PersonRepository {

    override val repository: PersonRepository.Service = new PersonRepository.Service {
        def get(id: String): ZIO[Any, ExpectedFailure, Option[Person]] =
            for {
                person <- ref.get.map(_.get(id))
                u <- person match {
                    case Some(s) => ZIO.some(s)
                    case None => ZIO.none
                }
            } yield {
                u
            }

        def create(person: Person): ZIO[Any, ExpectedFailure, Unit] = ref.update(map => map.+(person.id -> person)).unit

        def delete(id: String): ZIO[Any, ExpectedFailure, Unit] = ref.update(map => map - id).unit
    }

}

object InMemoryPersonRepository {
    def layer: ZLayer[Any, Nothing, InMemoryPersonRepository] = ZLayer.fromZIO(
      Ref.make(mutable.Map.empty[String, Person]).map(InMemoryPersonRepository.apply)
    )
}
