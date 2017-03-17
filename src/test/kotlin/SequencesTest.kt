import common.utils.merge
import org.junit.Test
import org.junit.Assert.*

class SequencesTest {

    @Test fun testAssertEquals() {
        assertSequenceEquals(seq(), seq())
        assertSequenceEquals(seq(0, 1, 2, 4, 8, 12, 41), seq(0, 1, 2, 4, 8, 12, 41))
        try {
            assertSequenceEquals(seq(1, 2, 3), seq(1, 2))
            fail()
        } catch (ignore: AssertionError) {
        }
    }

    @Test fun testMerge() {
        assertSequenceEquals(seq(0, 1, 2, 4, 8, 12, 41), merge(seq(1, 4, 8), seq(0, 1, 2, 8, 12, 41)))
        assertSequenceEquals(seq(), merge(seq<Int>(), seq<Int>()))
        assertSequenceEquals(seq(1, 2, 2, 3), merge(seq(1, 2, 2), seq(2, 3)))
        assertSequenceEquals(seq(1, 2, 3, 2, 4, 8), merge(seq(2, 4, 8), seq(1, 3, 2))) //
    }

    fun <T> seq(vararg elements: T): Sequence<T> = sequenceOf(*elements)

    fun assertSequenceEquals(expected: Sequence<Any?>, actual: Sequence<Any?>) {
        try {
            if (expected == actual) return
            val ai = actual.iterator()
            for (e in expected) {
                assertTrue("expected more elements", ai.hasNext())
                assertEquals(e, ai.next())
            }
            assertFalse("expected less elements", ai.hasNext())
        } catch (e: AssertionError) {
            throw AssertionError("\nExpected :${expected.joinToString()}\nActual   :${actual.joinToString()}", e)
        }
    }
}