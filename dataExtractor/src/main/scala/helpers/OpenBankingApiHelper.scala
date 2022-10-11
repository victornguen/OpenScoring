package helpers

import java.util.UUID

object OpenBankingApiHelper {
    def generateInteractionId: String = UUID.randomUUID().toString

}
