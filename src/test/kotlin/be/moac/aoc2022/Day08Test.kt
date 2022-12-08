package be.moac.aoc2022

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day08Test {

       val input = """
           30373
           25512
           65332
           33549
           35390
       """.trimIndent().lines()

    @Test
    fun `count the visible trees`() {
        assertThat(Day08 partOne input).isEqualTo(21)
    }

    @Test
    fun `calculate scenic score`() {
        assertThat(Day08 partTwo input).isEqualTo(8)
    }

}
