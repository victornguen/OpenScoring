package service

import com.typesafe.config.ConfigObject
import helpers.ConfigHelper
import service.typed.Uri

import scala.language.implicitConversions

package object syntax {

    implicit class ConfigObjectOps(obj: ConfigObject) {
        def toMap[A, B](fa: String => A, fb: AnyRef => B): Map[A, B] = ConfigHelper.toMap(obj)(fa, fb)

        def toMap[A](f: AnyRef => A): Map[String, A] = ConfigHelper.toMap(obj)(identity, f)
    }

    implicit def stringToUriType(raw: String): Uri = Uri(raw)

    implicit def intToUriType(raw:Int): Uri = Uri(raw.toString)

    implicit def longToUriType(raw:Long): Uri = Uri(raw.toString)

    import helpers.TypeHelper._

}
