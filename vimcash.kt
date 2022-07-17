//usr/bin/env [ $0 -nt $0.jar ] && kotlinc -d $0.jar $0; [ $0.jar -nt $0 ] && java -cp $CLASSPATH:$0.jar VimcashKt $@; exit 0

import java.io.File

fun String.parseAmount(account: String): Double {
    return substring(21, 33).toDouble() * if (indexOf(" $account ") > 33) 1 else -1
}

fun File.calculateBalance(account: String, currency: String): Double {
    val filterRegex = Regex(".{14}\\|. $currency [0-9. ]+[^ ]* $account .*")
    return readLines()
        .filter { it.matches(filterRegex) }
        .map { it.parseAmount(account) }
        .sum()
}
 
fun File.calculateBalances(account: String): Map<String, Double> {
    val filterRegex = Regex(".{14}\\|. ... [0-9. ]+[^ ]* $account .*")
    val results = mutableMapOf<String, Double>()
    readLines()
        .filter { it.matches(filterRegex) }
        .forEach { 
            val currency = it.slice(17..19)
            val amount = it.parseAmount(account)
            results.put(currency, results.get(currency)?.plus(amount) ?: amount) 
        }
    return results
}

fun File.collectAccounts(): Set<String> {
    return readLines()
        .drop(1)
        .flatMap { it.substring(33, 50).split(' ') }
        .toSortedSet()
}
