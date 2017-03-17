/**
 * Created by Artem on 10.03.2017.
 */

data class Point(val x: Float, val y: Float, val z: Float)

class Group<T>(className: String, s :Sequence<T>): Sequence<T> by s {
}
//fun line(): Sequence<Point>