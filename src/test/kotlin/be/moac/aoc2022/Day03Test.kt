package be.moac.aoc2022

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class Day03Test {

    @Test
    fun `sum of rucksack priorities`() {
        val input = listOf(
            "vJrwpWtwJgWrhcsFMMfFFhFp",
            "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL",
            "PmmdzqPrVvPwwTWBwg",
            "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn",
            "ttgJtRGJQctTZtZT",
            "CrZsJsPPZsGzwwsLwLmpwMDw",
        )


        assertThat(Day03 partOne input).isEqualTo(157)
        assertThat(Day03 partTwo input).isEqualTo(70)
    }
}
