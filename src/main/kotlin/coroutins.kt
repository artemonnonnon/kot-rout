import common.utils.*
import java.util.*
import kotlin.coroutines.experimental.buildSequence

/**
 * Created by Artem on 04.03.2017.
 */

val fibonacci = buildSequence {
    yield(1) // first Fibonacci number
    var cur = 1
    var next = 1
    while (true) {
        yield(next) // next Fibonacci number
        val tmp = cur + next
        cur = next
        next = tmp
    }
}

fun main(args: Array<String>) {
//    val countedArgs = args.sorted().fold(LinkedHashMap<String, Int>(), ::addOne)

    val days = daysFromNow().map(::startOfDay).map(::Date)
    println(days.take(10).joinToString(separator = "\n"))
}

//inline fun <K> MutableMap<K, Int>.addOne(key: K): Int? = put(key, getOrDefault(key, 0) + 1)
fun <K> addOne(m: MutableMap<K, Int>, k: K): MutableMap<K, Int> {
    m.put(k, m.getOrDefault(k, 0) + 1)
    return m
}


