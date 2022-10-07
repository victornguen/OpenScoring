package auth

import pdi.jwt.{Jwt, JwtAlgorithm, JwtClaim}

import java.time.Clock

object Auth {

    private val SECRET_KEY = "secretKey"

    implicit val clock: Clock = Clock.systemUTC

    def jwtEncode(username: String): String = {
        val json  = s"""{"user": "$username"}"""
        val claim = JwtClaim {
            json
        }.issuedNow.expiresIn(300)
        Jwt.encode(claim, SECRET_KEY, JwtAlgorithm.HS512)
    }

    def jwtDecode(token: String): Option[JwtClaim] = {
        Jwt.decode(token, SECRET_KEY, Seq(JwtAlgorithm.HS512)).toOption
    }

}
