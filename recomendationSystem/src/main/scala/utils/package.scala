import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

package object utils {
    final case class Years(value: Double) extends AnyVal
    object Years {
        implicit val encoder: JsonEncoder[Years] = DeriveJsonEncoder.gen[Years]
        implicit val decoder: JsonDecoder[Years] = DeriveJsonDecoder.gen[Years]
    }

}
