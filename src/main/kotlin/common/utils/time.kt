package common.utils

import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.getOrSet

val SECOND = 1000
val MINUTE = SECOND * 60
val HOUR = MINUTE * 60
val DAY = HOUR * 24

val DATE_FORMAT = "yyyy-MM-dd"
val DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"

private val formats = ThreadLocal<MutableMap<String, SimpleDateFormat>>()
private fun getFormatter(format: String): SimpleDateFormat {
    var sdf: SimpleDateFormat? = formats.getOrSet(::HashMap)[format]
    if (sdf == null) {
        sdf = SimpleDateFormat(format)
        formats.get()[format] = sdf
    }
    return sdf
}

fun parseDate(s: String, format: String): Date = getFormatter(format).parse(s)
fun parseDate(s: String): Date = parseDate(s, DATE_FORMAT)
fun parseTime(s: String): Date = parseDate(s, DATE_TIME_FORMAT)

fun formatDate(d: Date, format: String): String = getFormatter(format).format(d)
fun formatDate(t: Long, format: String): String = formatDate(Date(t), format)
fun formatDate(d: Date): String = formatDate(d, DATE_FORMAT)
fun formatDate(t: Long): String = formatDate(t, DATE_FORMAT)
fun formatTime(d: Date): String = formatDate(d, DATE_TIME_FORMAT)
fun formatTime(t: Long): String = formatDate(t, DATE_TIME_FORMAT)

fun nextDay(time: Long): Long = with(calendar(time)) {
    add(Calendar.DAY_OF_MONTH, 1)
    return timeInMillis
}

fun nextHour(time: Long): Long = with(calendar(time)) {
    add(Calendar.HOUR_OF_DAY, 1)
    return timeInMillis
}

fun startOfDay(time: Long): Long = with(calendar(time)) {
    set(Calendar.HOUR_OF_DAY, 0)
    set(Calendar.MINUTE, 0)
    set(Calendar.SECOND, 0)
    set(Calendar.MILLISECOND, 0)
    return timeInMillis
}

fun startOfWeek(time: Long): Long = with(calendar(time)) {
    set(Calendar.DAY_OF_WEEK, 0)
    set(Calendar.HOUR_OF_DAY, 0)
    set(Calendar.MINUTE, 0)
    set(Calendar.SECOND, 0)
    set(Calendar.MILLISECOND, 0)
    return timeInMillis
}

fun startOfMonth(time: Long): Long = with(calendar(time)) {
    set(Calendar.DAY_OF_MONTH, 0)
    set(Calendar.HOUR_OF_DAY, 0)
    set(Calendar.MINUTE, 0)
    set(Calendar.SECOND, 0)
    set(Calendar.MILLISECOND, 0)
    return timeInMillis
}

fun startOfNextDay(time: Long): Long = with(calendar(time)) {
    add(Calendar.DAY_OF_MONTH, 1)
    set(Calendar.HOUR_OF_DAY, 0)
    set(Calendar.MINUTE, 0)
    set(Calendar.SECOND, 0)
    set(Calendar.MILLISECOND, 0)
    return timeInMillis
}

fun startOfHour(time: Long): Long = with(calendar(time)) {
    set(Calendar.MINUTE, 0)
    set(Calendar.SECOND, 0)
    set(Calendar.MILLISECOND, 0)
    return timeInMillis
}

fun daysFromNow(): Sequence<Long> = daysSequence(now())
fun daysSequence(from: Long): Sequence<Long> = sequenceOnCalendar(from) { add(Calendar.DAY_OF_MONTH, 1) }
fun hoursSequence(from: Long): Sequence<Long> = sequenceOnCalendar(from) { add(Calendar.HOUR_OF_DAY, 1) }
fun weeksSequence(from: Long): Sequence<Long> = sequenceOnCalendar(from) { add(Calendar.WEEK_OF_YEAR, 1) }
fun monthsSequence(from: Long): Sequence<Long> = sequenceOnCalendar(from) { add(Calendar.MONTH, 1) }

fun hoursOfDay(from: Long): Sequence<Long> = toTheEndOfTheDay(from) { add(Calendar.HOUR_OF_DAY, 1) }

inline fun toTheEndOfTheDay(from: Long, crossinline step: Calendar.() -> Unit): Sequence<Long> = sequenceOnMutable(
        init = { with(calendar(from)) { Pair(this, this.get(Calendar.DAY_OF_MONTH)) } },
        out = { first.timeInMillis },
        hasNext = { first.get(Calendar.DAY_OF_MONTH) == second },
        step = { first.step() }
)

fun twentyFourHours(from: Long): Sequence<Long> = hoursSequence(from).take(24)

fun now(): Long = Date().time

fun calendar(from: Long): Calendar = Calendar.getInstance().apply { timeInMillis = from }

inline fun <T> sequenceOnCalendar(
        from: Long,
        crossinline out: Calendar.() -> T,
        crossinline hasNext: Calendar.() -> Boolean,
        crossinline step: Calendar.() -> Unit): Sequence<T> = sequenceOnMutable({ calendar(from) }, out, hasNext, step)

inline fun <T> sequenceOnCalendar(
        from: Long,
        crossinline out: Calendar.() -> T,
        crossinline step: Calendar.() -> Unit): Sequence<T> = sequenceOnCalendar(from, out, { true }, step)

inline fun sequenceOnCalendar(
        from: Long,
        crossinline step: Calendar.() -> Unit): Sequence<Long> = sequenceOnCalendar(from, { timeInMillis }, { true }, step)
