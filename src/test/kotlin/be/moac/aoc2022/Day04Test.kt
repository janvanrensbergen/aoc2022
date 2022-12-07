package be.moac.aoc2022

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class Day04Test {

       @Test
    fun `find overlapping sections`() {
        val input = listOf(
            "2-4,6-8",
            "2-3,4-5",
            "5-7,7-9",
            "2-8,3-7",
            "6-6,4-6",
            "2-6,4-8",
        )


        assertThat(Day04 partOne input).isEqualTo(2)
        assertThat(Day04 partTwo input).isEqualTo(4)
    }
}
