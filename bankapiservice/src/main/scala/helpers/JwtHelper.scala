package helpers

import com.typesafe.config.ConfigFactory
import pdi.jwt.{Jwt, JwtAlgorithm, JwtClaim}
import pdi.jwt.JwtZIOJson
import pdi.jwt.algorithms.JwtHmacAlgorithm

import java.time.Clock
import scala.util.Try

object JwtHelper {
    private val config = ConfigFactory.load()

    private val SECRET_KEY = Try(config.getString("secretKey")).getOrElse("secret")

    val algorithm: JwtHmacAlgorithm = JwtAlgorithm.fromString(
        Try(config.getString("jwtEncryption.algorithm")).getOrElse("HS512")
    ).asInstanceOf[JwtHmacAlgorithm]

    implicit val clock: Clock = Clock.systemUTC

    def jwtEncode(username: String): String = {
        val json  = s"""{"user": "$username"}"""
        val claim = JwtClaim {
            json
        }.issuedNow.expiresIn(Int.MaxValue)
        JwtZIOJson.encode(claim, SECRET_KEY, algorithm)
    }

    def jwtDecode(token: String): Option[JwtClaim] = {
        JwtZIOJson.decode(token, SECRET_KEY, Seq(algorithm)).toOption
    }

}
