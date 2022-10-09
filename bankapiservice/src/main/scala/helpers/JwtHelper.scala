package helpers

import com.typesafe.config.ConfigFactory
import pdi.jwt.{Jwt, JwtAlgorithm, JwtClaim}
import pdi.jwt.JwtZIOJson

import java.time.Clock
import scala.util.Try

object JwtHelper {
    private val config = ConfigFactory.load()

    private val SECRET_KEY = Try(config.getString("secretKey")).getOrElse("secret")

    val algorithm: JwtAlgorithm = JwtAlgorithm.fromString(
        Try(config.getString("jwtEncryption.algorithm")).getOrElse("HS512")
    )

    implicit val clock: Clock = Clock.systemUTC

    def jwtEncode(username: String): String = {
        val json  = s"""{"user": "$username"}"""
        val claim = JwtClaim {
            json
        }.issuedNow.expiresIn(Int.MaxValue)
        JwtZIOJson.encode(claim, SECRET_KEY, JwtAlgorithm.HS512)
    }

    def jwtDecode(token: String): Option[JwtClaim] = {
        JwtZIOJson.decode(token, SECRET_KEY, Seq(JwtAlgorithm.HS512)).toOption
    }

}
