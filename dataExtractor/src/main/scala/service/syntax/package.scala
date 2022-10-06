package service

import com.typesafe.config.ConfigObject
import service.typed.Uri

import scala.jdk.CollectionConverters.MapHasAsScala
import scala.language.implicitConversions

package object syntax {

    implicit class ConfigObjectOps(obj: ConfigObject) {
        def toMap[A, B](fa: String => A, fb: AnyRef => B): Map[A, B] = obj.unwrapped().asScala.toMap.map {
            case (key, value) => (fa(key), fb(value))
        }

        def toMap[A](f: AnyRef => A): Map[String, A] = toMap(identity, f)
    }

    implicit def stringToUriType(raw:String): Uri = Uri(raw)

}
