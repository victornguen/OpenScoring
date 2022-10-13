package com.scalalazy.mainservice.module
import com.scalalazy.mainservice.model.ExpectedFailure
import com.scalalazy.mainservice.model.database.Person
import zio.ZIO

package object PersonDB {
    def get(id: String): ZIO[PersonRepository, ExpectedFailure, Option[Person]] =
        ZIO.serviceWithZIO(_.repository.get(id))

    def create(person: Person): ZIO[PersonRepository, ExpectedFailure, Unit] =
        ZIO.serviceWithZIO(_.repository.create(person))

    def delete(id: String): ZIO[PersonRepository, ExpectedFailure, Unit] =
        ZIO.serviceWithZIO(_.repository.delete(id))
}
