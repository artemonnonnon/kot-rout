fun main(args: Array<String>) {
    val o = object {
        val a = 13
        val b = "B"
    }
    println(" It's an obj!!! {${o.a}, ${o.b}}")
}

class table<H, C>(header: Sequence<H>, rows: Sequence<Sequence<C>>) {

}
