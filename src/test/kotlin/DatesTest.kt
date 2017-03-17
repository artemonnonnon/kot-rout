import org.junit.Test
import org.junit.Assert.*
import common.utils.*

class DatesTest {

    @Test fun parse() {
        val d = parseDate("2017-03-17").time
        assertEquals("2017-03-17 00:00:00", formatTime(d))
        assertEquals("2017-03-17", formatDate(d))

        val t = parseTime("2017-03-17 12:30:00").time
        assertEquals("2017-03-17 12:30:00", formatTime(t))
        assertEquals("2017-03-17", formatDate(t))
    }

    @Test fun daysStream() {
        val from = parseTime("2017-03-17 12:30:00").time
        val days = daysSequence(from)

        val iterator = days.iterator()
        assertEquals("2017-03-17 12:30:00", formatTime(iterator.next()))
    }

    @Test fun hoursOfHalfDaySeq() {
        val from = parseTime("2017-03-17 12:30:00").time
        val hours = hoursOfDay(from)
        val iterator = hours.iterator()

        assertEquals("2017-03-17 12:30:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 13:30:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 14:30:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 15:30:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 16:30:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 17:30:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 18:30:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 19:30:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 20:30:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 21:30:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 22:30:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 23:30:00", formatTime(iterator.next()))
        assertFalse(iterator.hasNext())
    }

    @Test fun hoursOfDaySeq() {
        val from = parseDate("2017-03-17").time
        val hours = hoursOfDay(from)
        val iterator = hours.iterator()

        assertEquals("2017-03-17 00:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 01:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 02:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 03:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 04:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 05:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 06:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 07:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 08:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 09:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 10:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 11:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 12:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 13:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 14:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 15:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 16:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 17:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 18:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 19:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 20:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 21:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 22:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 23:00:00", formatTime(iterator.next()))
        // 2017-03-17 24:30:00 is 2017-03-18 00:30:00
        assertFalse(iterator.hasNext())
    }

    @Test fun hoursOfDaySeq2() {
        val from = parseDate("2017-03-17").time
        val nextDay = startOfNextDay(from)
        val hours = hoursSequence(from).takeWhile { it < nextDay }
        val iterator = hours.iterator()

        assertEquals("2017-03-17 00:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 01:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 02:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 03:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 04:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 05:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 06:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 07:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 08:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 09:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 10:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 11:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 12:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 13:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 14:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 15:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 16:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 17:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 18:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 19:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 20:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 21:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 22:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 23:00:00", formatTime(iterator.next()))
    }

    @Test fun hoursSeq() {
        val from = parseDate("2017-03-17").time
        val hours = hoursSequence(from)
        val iterator = hours.iterator()

        assertEquals("2017-03-17 00:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 01:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 02:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 03:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 04:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 05:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 06:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 07:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 08:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 09:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 10:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 11:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 12:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 13:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 14:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 15:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 16:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 17:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 18:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 19:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 20:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 21:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 22:00:00", formatTime(iterator.next()))
        assertEquals("2017-03-17 23:00:00", formatTime(iterator.next()))
    }

    @Test fun weeksSeq() {
        val from = parseDate("2017-03-17").time
        val weeks = weeksSequence(from)
        val iterator = weeks.iterator()

        assertEquals("2017-03-17", formatDate(iterator.next()))
        assertEquals("2017-03-24", formatDate(iterator.next()))
        assertEquals("2017-03-31", formatDate(iterator.next()))
        assertEquals("2017-04-07", formatDate(iterator.next()))
        assertEquals("2017-04-14", formatDate(iterator.next()))
        assertEquals("2017-04-21", formatDate(iterator.next()))
    }

    @Test fun monthsSeq() {
        val from = parseDate("2017-03-17").time
        val months = monthsSequence(from)
        val iterator = months.iterator()

        assertEquals("2017-03-17", formatDate(iterator.next()))
        assertEquals("2017-04-17", formatDate(iterator.next()))
        assertEquals("2017-05-17", formatDate(iterator.next()))
        assertEquals("2017-06-17", formatDate(iterator.next()))
        assertEquals("2017-07-17", formatDate(iterator.next()))
        assertEquals("2017-08-17", formatDate(iterator.next()))
        assertEquals("2017-09-17", formatDate(iterator.next()))
        assertEquals("2017-10-17", formatDate(iterator.next()))
        assertEquals("2017-11-17", formatDate(iterator.next()))
        assertEquals("2017-12-17", formatDate(iterator.next()))
        assertEquals("2018-01-17", formatDate(iterator.next()))
        assertEquals("2018-02-17", formatDate(iterator.next()))
        assertEquals("2018-03-17", formatDate(iterator.next()))
    }

    @Test fun mergeSeq() {
        val from = parseDate("2017-03-17 12:30:00").time
        val months = monthsSequence(startOfMonth(from)).map { Pair(it, {"------"}) }
        val days = daysSequence(from).map { Pair(it, {formatTime(it)}) }
        merge(months, days, {a, b -> a.first.compareTo(b.first)}).take(100).map { it.second() }.forEach(::println)
    }
}