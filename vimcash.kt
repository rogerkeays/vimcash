//usr/bin/env [ $0 -nt $0.jar ] && kotlinc -d $0.jar $0; [ $0.jar -nt $0 ] && kotlin -cp $0.jar VimcashKt $@; exit 0

import java.io.File
import java.util.TreeSet

data class Split(
  val seq: String,
  val description: String,
  val currency: String,
  val amount: Double,
  val debitAccount: String,
  val creditAccount: String
)

fun String.toSplit(): Split {
    var (seq, description, currency, amount, debitAccount, creditAccount) = toSplitRegex.find(this)!!.destructured
    if (amount.equals("?")) amount = "0"
    return Split(seq, description, currency, amount.toDouble(), debitAccount, creditAccount)
}
val toSplitRegex = Regex("^([0-9A-Z_]{13}) .\\|  ([^:]+): +([A-Za-z]+) +([-0-9.?]+) +([^ ]+) +([^ ]+)")

fun Split.hasAccount(account: String): Boolean {
    return debitAccount.equals(account) || creditAccount.equals(account)
}

fun Split.amountFor(account: String): Double {
    if (debitAccount.equals(account)) {
        return amount
    } else if (creditAccount.equals(account)) {
        return amount * -1
    } else {
        return 0.0
    }
}

fun File.calculateBalance(account: String, currency: String): Double {
    return readLines()
        .map { it.toSplit() }
        .filter { it.hasAccount(account) && it.currency.equals(currency) }
        .sumOf { it.amountFor(account) }
}
 
fun File.calculateBalances(account: String): Map<String, Double> {
    val results = mutableMapOf<String, Double>()
    readLines()
        .map { it.toSplit() }
        .filter { it.hasAccount(account) }
        .forEach { results.put(it.currency,
                   results.get(it.currency)?.plus(it.amountFor(account)) ?: it.amountFor(account)) }
    return results
}

fun File.collectAccounts(): Set<String> {
    val results = TreeSet<String>()
    readLines()
        .map { it.toSplit() }
        .forEach { results.add(it.creditAccount); results.add(it.debitAccount) }
    return results
}

fun File.reportBalances() {
     collectAccounts()
         .filter { it.matches(allCapsRegex) }
         .associate { Pair(it, calculateBalances(it).filter { it.value.abs() > 0.005 }) }
         .filter { it.value.size > 0 }
         .forEach { println(it) }
}
val allCapsRegex = Regex("[0-9A-Z]+")

fun Double.abs(): Double = kotlin.math.abs(this)

