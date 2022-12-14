package be.moac.aoc2022

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day14Test {
    
    private val input = """
    498,4 -> 498,6 -> 496,6
    503,4 -> 502,4 -> 502,9 -> 494,9
    """.trimIndent().lines()

    @Test
    fun `part one`() {
        assertThat(Day14 partOne input).isEqualTo(24)
    }

    @Test
    fun `part two`() {
        assertThat(Day14 partTwo input).isEqualTo(93)
    }
}
