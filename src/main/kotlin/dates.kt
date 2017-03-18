import common.utils.*

fun main(args: Array<String>) {
    val o = object {
        val a = 13
        val b = "B"
    }
    println(" It's an obj!!! {${o.a}, ${o.b}}")

    var a: String? = null
    if (Math.random() > 0.5) a = "40"
    println(a ifNull "25")
    var b = 11
    println(b or 22)
}

class table<H, C>(header: Sequence<H>, rows: Sequence<Sequence<C>>) {

}
