import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

package object utils {
    final case class Month(value: Double) extends AnyVal
    object Month {
        implicit val encoder: JsonEncoder[Month] = DeriveJsonEncoder.gen[Month]
        implicit val decoder: JsonDecoder[Month] = DeriveJsonDecoder.gen[Month]
    }

}
