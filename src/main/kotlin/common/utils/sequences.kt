package common.utils

import kotlin.coroutines.experimental.buildSequence

fun <T> const(value: T): Sequence<T> = buildSequence {
    while (true) {
        yield(value)
    }
}

inline fun <T> sequence(first: T?, crossinline next: (T) -> T?): Sequence<T> = buildSequence {
    var i = first
    while (i != null) {
        yield(i!!)
        i = next(i)
    }
}

fun <T> Iterator<T>.nextOrNull(): T? = if (hasNext()) next() else null

fun <T> merge(a: Sequence<T>, b: Sequence<T>, compare: (a: T, b: T) -> Int): Sequence<T> = Sequence {
    MergedIterator(a.iterator(), b.iterator(), compare)
}

fun <T : Comparable<T>> merge(a: Sequence<T>, b: Sequence<T>): Sequence<T> = merge(a, b, { a, b -> a.compareTo(b) })

class MergedIterator<T>(a: Iterator<T>, b: Iterator<T>, val compare: (a: T, b: T) -> Int) : Iterator<T> {
    val ai = LookForwardIterator(a)
    val bi = LookForwardIterator(b)

    override fun hasNext(): Boolean = ai.hasNext() || bi.hasNext()

    override fun next(): T = when {
        !ai.hasNext() -> bi.next()
        !bi.hasNext() -> ai.next()
        else -> {
            val i = compare(ai.lookNext(), bi.lookNext())
            when {
                i > 0 -> bi.next()
                i < 0 -> ai.next()
                else -> {
                    ai.next(); bi.next()
                }
            }
        }
    }
}

class LookForwardIterator<out T>(val i: Iterator<T>) : Iterator<T> {

    private var nextNext: T? = null

    override fun hasNext(): Boolean = nextNext != null || i.hasNext()

    override fun next(): T = when (nextNext) {
        null -> i.next()
        else -> nextNext.apply { nextNext = null }!!
    }

    fun lookNext(): T = when {
        nextNext != null -> nextNext!!
        else -> {
            nextNext = i.next()
            nextNext!!
        }
    }
}

inline fun <T, K> fraction(seq: Sequence<T>, crossinline f: (T) -> Sequence<K>): Sequence<K> = buildSequence {
    seq.forEach {
        f(it).forEach { yield(it) }
    }
}

inline fun <A, B, C> combo1_1(a: Sequence<A>, b: Sequence<B>, crossinline f: (A, B) -> C): Sequence<C> = buildSequence {
    val ai = a.iterator()
    val bi = b.iterator()
    while (ai.hasNext() && bi.hasNext()) {
        yield(f(ai.next(), bi.next()))
    }
}

inline fun <A, B, C> combo(a: Sequence<A>, b: Sequence<B>, crossinline f: (A, B) -> C): Sequence<C> = Sequence {
    object : Iterator<C> {
        val ai = a.iterator()
        val bi = b.iterator()
        override fun hasNext(): Boolean = ai.hasNext() && bi.hasNext()
        override fun next(): C = f(ai.next(), bi.next())
    }
}

inline fun <T, M> sequenceOnMutable1_1(
        crossinline init: () -> M,
        crossinline out: M.() -> T,
        crossinline hasNext: M.() -> Boolean,
        crossinline step: M.() -> Unit): Sequence<T> = buildSequence {
    val mutable = init()
    while (mutable.hasNext()) {
        yield(mutable.out())
        mutable.step()
    }
}

inline fun <T, M> sequenceOnMutable(
        crossinline init: () -> M,
        crossinline out: M.() -> T,
        crossinline hasNext: M.() -> Boolean,
        crossinline step: M.() -> Unit): Sequence<T> = Sequence {
    object : Iterator<T> {
        val mutable = init()
        override fun hasNext(): Boolean = mutable.hasNext()
        override fun next(): T {
            return mutable.out().apply { mutable.step() }
        }
    }
}