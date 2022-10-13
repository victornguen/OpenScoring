package com.scalalazy.mainservice.module
import com.scalalazy.mainservice.model.ExpectedFailure
import com.scalalazy.mainservice.model.database.CreditApplication
import zio.ZIO

package object CreditApplicationDB {
    def get(id: String): ZIO[CreditApplicationRepository, ExpectedFailure, Option[CreditApplication]] =
        ZIO.serviceWithZIO(_.repository.get(id))

    def create(creditApplication: CreditApplication): ZIO[CreditApplicationRepository, ExpectedFailure, Unit] =
        ZIO.serviceWithZIO(_.repository.create(creditApplication))

    def delete(id: String): ZIO[CreditApplicationRepository, ExpectedFailure, Unit] =
        ZIO.serviceWithZIO(_.repository.delete(id))
}
