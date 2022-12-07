package be.moac.aoc2022

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day05Test {

    @Test
    fun `rearrange crates`() {
        val input =
            """
                    [D]    
                [N] [C]    
                [Z] [M] [P]
                 1   2   3 
                
                move 1 from 2 to 1
                move 3 from 1 to 3
                move 2 from 2 to 1
                move 1 from 1 to 2
            """.trimIndent().lines()

        assertThat(Day05 partOne input).isEqualTo("CMZ")
        assertThat(Day05 partTwo input).isEqualTo("MCD")
    }

}
