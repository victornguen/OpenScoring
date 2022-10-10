package creditRules.storage

import creditRules.data.CreditRule
import zio.{Task, ZIO}

trait CreditRulesStorage {

    def addRule(rule: CreditRule): Task[String]

    def getRules: Task[List[CreditRule]]

}

object CreditRulesStorage {
    def addRule(rule: CreditRule): ZIO[CreditRulesStorage, Throwable, String] =
        ZIO.serviceWithZIO[CreditRulesStorage](_.addRule(rule))

    def getRules: ZIO[CreditRulesStorage, Throwable, List[CreditRule]] = ZIO.serviceWithZIO[CreditRulesStorage](_.getRules)
}
