package be.moac.aoc2022

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day12Test {

    @Test
    fun `find shortest path to best signal`() {
        assertThat(Day12 partOne input).isEqualTo(31)
    }

    private val input = """
        Sabqponm
        abcryxxl
        accszExk
        acctuvwj
        abdefghi
       """.trimIndent().lines()


}
