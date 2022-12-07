package be.moac.aoc2022

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day01Test {

    val input = listOf(
        "1000",
        "2000",
        "3000",
        ""    ,
        "4000",
        ""    ,
        "5000",
        "6000",
        ""    ,
        "7000",
        "8000",
        "9000",
        ""    ,
        "10000"
    )

    @Test
    fun `find the elve that carries the most callories`() {
        assertThat(Day01 partOne input).isEqualTo(24000L)
    }

    @Test
    fun `find the sum of the calories of the top 3 elves`() {
        assertThat(Day01 partTwo input).isEqualTo(45000L)
    }
}
