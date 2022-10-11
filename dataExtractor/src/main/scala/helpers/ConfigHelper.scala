package helpers

import com.typesafe.config.ConfigObject

import scala.jdk.CollectionConverters.MapHasAsScala

object ConfigHelper {
    def toMap[A, B](obj: ConfigObject)(fa: String => A, fb: AnyRef => B): Map[A, B] =
        obj.unwrapped().asScala.toMap.map {
            case (key, value) => (fa(key), fb(value))
        }

}
