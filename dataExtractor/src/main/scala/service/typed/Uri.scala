package service.typed

final case class Uri(raw: String) { self =>
    def concat(other: Uri): Uri = Uri.concat(self, other)

    def concat(other: String): Uri = Uri.concat(self, other)

    def /(other: Uri): Uri = concat(other)

    def /(other: String): Uri = concat(other)
}

object Uri {
    private def concatUriStrings(left: String, right: String): String = {
        val l = if (left.endsWith("/")) left.dropRight(1) else left
        val r = if (right.startsWith("/")) right.slice(1, right.length) else right
        l + "/" + r
    }

    def concat(left: Uri, right: Uri): Uri = Uri(concatUriStrings(left.raw, right.raw))

    def concat(left: Uri, right: String): Uri = Uri(concatUriStrings(left.raw, right))
}
