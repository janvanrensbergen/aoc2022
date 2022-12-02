package be.moac.oac2022

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class Day02Test {

    @Test
    fun `total score of rock paper scissor`() {
        val input = listOf(
            "A" to "Y",
            "B" to "X",
            "C" to "Z",
        )

        assertThat(Day02 partOne input).isEqualTo(15)
        assertThat(Day02 partTwo input).isEqualTo(12)
    }
}
