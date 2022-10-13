package com.scalalazy.mainservice.module.UserDB

import com.scalalazy.mainservice.model.ExpectedFailure
import com.scalalazy.mainservice.model.database.User
import zio.{Ref, ZIO, ZLayer}

import scala.collection.mutable

final case class InMemoryUserRepository(ref: Ref[mutable.Map[Long, User]]) extends UserRepository {

    override val repository: UserRepository.Service = new UserRepository.Service {
        def get(id: Long): ZIO[Any, ExpectedFailure, Option[User]] =
            for {
                user <- ref.get.map(_.get(id))
                u <- user match {
                    case Some(s) => ZIO.some(s)
                    case None => ZIO.none
                }
            } yield {
                u
            }

        def create(user: User): ZIO[Any, ExpectedFailure, Unit] = ref.update(map => map.+(user.id -> user)).unit

        def delete(id: Long): ZIO[Any, ExpectedFailure, Unit] = ref.update(map => map.-(id)).unit
    }

}

object InMemoryUserRepository {
    def layer: ZLayer[Any, Nothing, InMemoryUserRepository] = ZLayer.fromZIO(
      Ref.make(mutable.Map.empty[Long, User]).map(InMemoryUserRepository.apply)
    )
}
