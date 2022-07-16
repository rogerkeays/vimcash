//usr/bin/env [ $0 -nt $0.jar ] && kotlinc -d $0.jar $0; [ $0.jar -nt $0 ] && java -cp $CLASSPATH:$0.jar VimcashKt $@; exit 0

import java.io.File

fun File.calculateBalance(account: String, currency: String): Double {
    val filterRegex = Regex(".{14}\\|. $currency [0-9. ]+[^ ]* $account .*")
    return readLines()
        .filter { it.matches(filterRegex) }
        .map { it.substring(21, 33).toDouble() * if (it.indexOf(account) > 33) 1 else -1 }
        .sum()
}
 
