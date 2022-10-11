package service.services

import auth.Auth.jwtEncode
import service.models.User
import zio.{ULayer, ZIO, ZLayer}
import com.github.t3hnar.bcrypt._

import java.util.UUID
import scala.collection.mutable
import scala.runtime.Nothing$

trait UserRepository {
    def addUser(username: String, password: String): ZIO[Any, Throwable, Boolean]

    def authorizeUser(username: String, password: String): ZIO[Any, Nothing, String]

    def deleteUser(username: String): ZIO[Any, Throwable, Boolean]
}

object UserRepository {
    val layer: ULayer[UserRepositoryTestImpl] = ZLayer.succeed(UserRepositoryTestImpl())

    def addUser(username: String, password: String): ZIO[UserRepository, Throwable, Boolean] =
        ZIO.serviceWithZIO[UserRepository](_.addUser(username, password))

    def authorizeUser(username: String, password: String): ZIO[UserRepository, Nothing, String] =
        ZIO.serviceWithZIO[UserRepository](_.authorizeUser(username, password))

    def deleteUser(username: String): ZIO[UserRepository, Throwable, Boolean] =
        ZIO.serviceWithZIO[UserRepository](_.deleteUser(username))

}

final case class UserRepositoryTestImpl() extends UserRepository {
    val users: mutable.HashSet[User] = mutable.HashSet[User]()

    override def addUser(username: String, password: String): ZIO[Any, Throwable, Boolean] = ZIO.from {
        val salt: String              = generateSalt
        val encryptedPassword: String = password.bcryptBounded(salt)
        val newUser: User             = User(
            id = UUID.randomUUID().toString,
            username = username,
            passwordHash = encryptedPassword,
            salt = salt
        )
        users.add(newUser)
    }

    override def authorizeUser(
        username: String,
        password: String
      ): ZIO[Any, Nothing, String] = ZIO.fromOption {
        users
            .find(_.username == username)
            .filter(user =>
                user.passwordHash == password.bcryptBounded(user.salt)
            )
            .map { user => jwtEncode(user.username) }
    }.orElse(ZIO.succeed(""))

    def deleteUser(username: String): ZIO[Any, Throwable, Boolean] =
        for {
            userToDelete <- ZIO.fromOption(users.find(_.username == username))
                                .orElseFail(new Throwable(s"user with username = $username not found"))
        } yield users.remove(userToDelete)

}

//noinspection SimplifyForeachInspection
object UserRepositoryTestImpl {
    def apply(users: (String, String)*): ZIO[Any, Throwable, UserRepositoryTestImpl] = {
        val newRepository = UserRepositoryTestImpl()
        for {
            _ <- ZIO.foreach(users) {
                     case (username, password) => newRepository.addUser(username, password)
                 }
        } yield newRepository
    }


}
