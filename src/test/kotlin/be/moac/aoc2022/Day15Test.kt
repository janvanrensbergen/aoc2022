package be.moac.aoc2022

import be.moac.aoc2022.Day15.Beacon
import be.moac.aoc2022.Day15.Signal
import be.moac.aoc2022.Day15.Point
import be.moac.aoc2022.Day15.Sensor
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.SoftAssertions.assertSoftly
import org.junit.jupiter.api.Test

class Day15Test {

    private val input = """
        Sensor at x=2, y=18: closest beacon is at x=-2, y=15
        Sensor at x=9, y=16: closest beacon is at x=10, y=16
        Sensor at x=13, y=2: closest beacon is at x=15, y=3
        Sensor at x=12, y=14: closest beacon is at x=10, y=16
        Sensor at x=10, y=20: closest beacon is at x=10, y=16
        Sensor at x=14, y=17: closest beacon is at x=10, y=16
        Sensor at x=8, y=7: closest beacon is at x=2, y=10
        Sensor at x=2, y=0: closest beacon is at x=2, y=10
        Sensor at x=0, y=11: closest beacon is at x=2, y=10
        Sensor at x=20, y=14: closest beacon is at x=25, y=17
        Sensor at x=17, y=20: closest beacon is at x=21, y=22
        Sensor at x=16, y=7: closest beacon is at x=15, y=3
        Sensor at x=14, y=3: closest beacon is at x=15, y=3
        Sensor at x=20, y=1: closest beacon is at x=15, y=3
    """.trimIndent().lines()

    @Test
    fun `part one`() {
        assertThat(Day15.partOne(input, 10L)).isEqualTo(26)
    }

    @Test
    fun `part two`() {
        assertThat(Day15.partTwo(input, 0, 20L)).isEqualTo(56000011)
    }


    @Test
    fun `find edges`() {
        val signal = Signal(Sensor(5,5), Beacon(0,5))
        val edges = signal.edges()
        assertSoftly { softly ->
            softly.assertThat(signal.distance).isEqualTo(5)
            softly.assertThat(edges).contains(Point(-1,5))
            softly.assertThat(edges).contains(Point(5, -1))
            softly.assertThat(edges).contains(Point(11,5))
            softly.assertThat(edges).contains(Point(5,11))
        }

    }
}
