package be.moac.aoc2022

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.Test

class Day09Test {
    private val input = """
           R 4
           U 4
           L 3
           D 1
           R 4
           D 1
           L 5
           R 2
       """.trimIndent().lines()

    private val otherInput = """
        R 5
        U 8
        L 8
        D 3
        R 17
        D 10
        L 25
        U 20
    """.trimIndent().lines()

    @Test
    fun `count positions tail visited at least once`() {
        assertThat(Day09 partOne input).isEqualTo(13)
    }

    @Test
    fun `count positions last knot of tail visited at least once`() {
        assertThat(Day09 partTwo input).isEqualTo(1)
//        assertThat(Day09 partTwo otherInput).isEqualTo(36)
    }

    @Test
    fun `that position can see if other position is touching`() {

        SoftAssertions.assertSoftly { softly ->
            softly.assertThat(Position(0, 0) isTouching Position(-1, 1)).`as`("1").isTrue
            softly.assertThat(Position(0, 0) isTouching Position(0, 1)).`as`("2").isTrue
            softly.assertThat(Position(0, 0) isTouching Position(1, 1)).`as`("3").isTrue
            softly.assertThat(Position(0, 0) isTouching Position(-1, 0)).`as`("4").isTrue
            softly.assertThat(Position(0, 0) isTouching Position(0, 0)).`as`("5").isTrue
            softly.assertThat(Position(0, 0) isTouching Position(1, 0)).`as`("6").isTrue
            softly.assertThat(Position(0, 0) isTouching Position(-1, -1)).`as`("7").isTrue
            softly.assertThat(Position(0, 0) isTouching Position(0, -1)).`as`("8").isTrue
            softly.assertThat(Position(0, 0) isTouching Position(1, -1)).`as`("9").isTrue

            softly.assertThat(Position(0, 0) isTouching Position(2, 0)).`as`("10").isFalse
            softly.assertThat(Position(0, 0) isTouching Position(0, 2)).`as`("10").isFalse
            softly.assertThat(Position(0, 0) isTouching Position(0, -2)).`as`("10").isFalse
            softly.assertThat(Position(0, 0) isTouching Position(-5, 0)).`as`("10").isFalse
            softly.assertThat(Position(0, 0) isTouching Position(-5, -5)).`as`("10").isFalse
            softly.assertThat(Position(0, 0) isTouching Position(5, 5)).`as`("10").isFalse
        }
    }

    @Test
    fun `that position can move to other position so they touch each other`() {

        SoftAssertions.assertSoftly { softly ->
            softly.assertThat((Position(0, 0) moveCloserTo Position(0,0)).first).`as`("1").isEqualTo(Position(0 , 0))

            softly.assertThat((Position(-2, 0) moveCloserTo Position(0,0)).first).`as`("1").isEqualTo(Position(-1 , 0))
            softly.assertThat((Position(-5, 0) moveCloserTo Position(0,0)).first).`as`("1").isEqualTo(Position(-1 , 0))

            softly.assertThat((Position(2, 0) moveCloserTo Position(0,0)).first).`as`("1").isEqualTo(Position(1 , 0))
            softly.assertThat((Position(5, 0) moveCloserTo Position(0,0)).first).`as`("1").isEqualTo(Position(1 , 0))

            softly.assertThat((Position(0, -1) moveCloserTo Position(0,0)).first).`as`("1").isEqualTo(Position(0 , -1))
            softly.assertThat((Position(0, -1) moveCloserTo Position(0,0)).first).`as`("1").isEqualTo(Position(0 , -1))

            softly.assertThat((Position(0, 2) moveCloserTo Position(0,0)).first).`as`("1").isEqualTo(Position(0,1  ))
            softly.assertThat((Position(0, 5) moveCloserTo Position(0,0)).first).`as`("1").isEqualTo(Position(0,1 ))

            softly.assertThat((Position(-1, -2) moveCloserTo Position(0,0)).first).`as`("diagonal").isEqualTo(Position(0,-1 ))
            softly.assertThat((Position(-2, -1) moveCloserTo Position(0,0)).first).`as`("diagonal 2").isEqualTo(Position(-1,0 ))
            softly.assertThat((Position(-1, -1) moveCloserTo Position(0,0)).first).`as`("diagonal 3").isEqualTo(Position(-1,-1 ))
        }
    }

    @Test
    fun `calculate position to other position`() {
        SoftAssertions.assertSoftly { softly ->
            softly.assertThat(Position(0, 0) isLeftOf  Position(0,0)).`as`("1").isFalse
            softly.assertThat(Position(5, 0) isLeftOf  Position(0,0)).`as`("1").isFalse
            softly.assertThat(Position(-2, 0) isLeftOf  Position(0,0)).`as`("1").isTrue
            softly.assertThat(Position(-5, 0) isLeftOf  Position(0,0)).`as`("1").isTrue

            softly.assertThat(Position(0, 0) isRightOf   Position(0,0)).`as`("1").isFalse
            softly.assertThat(Position(-5, 0) isRightOf   Position(0,0)).`as`("1").isFalse
            softly.assertThat(Position(2, 0) isRightOf   Position(0,0)).`as`("1").isTrue
            softly.assertThat(Position(5, 0) isRightOf   Position(0,0)).`as`("1").isTrue

            softly.assertThat(Position(0,0 ) isAboveOf  Position(0,0)).`as`("1").isFalse
            softly.assertThat(Position(0,-5) isAboveOf  Position(0,0)).`as`("1").isFalse
            softly.assertThat(Position(0,2 ) isAboveOf  Position(0,0)).`as`("1").isTrue
            softly.assertThat(Position(0,5 ) isAboveOf  Position(0,0)).`as`("1").isTrue

            softly.assertThat(Position(0,0 ) isBelowOf Position(0,0)).`as`("1").isFalse
            softly.assertThat(Position(0,5 ) isBelowOf Position(0,0)).`as`("1").isFalse
            softly.assertThat(Position(0,-2) isBelowOf Position(0,0)).`as`("1").isTrue
            softly.assertThat(Position(0,-5) isBelowOf Position(0,0)).`as`("1").isTrue

        }
    }
}
