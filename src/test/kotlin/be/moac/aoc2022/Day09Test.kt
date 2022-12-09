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

    @Test
    fun `count positions tail visited at least once`() {
        assertThat(Day09 partOne input).isEqualTo(13)
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
        }
    }

    @Test
    fun `move to same column or row`() {
        SoftAssertions.assertSoftly { softly ->
            softly.assertThat(Position(-1, -1) moveToSameColumnOrRow  Position(0,0)).`as`("column row 1").isEqualTo(Position(-1,-1 ))
            softly.assertThat(Position(-5, -1) moveToSameColumnOrRow  Position(0,0)).`as`("column row 2").isEqualTo(Position(-5,0 ))
            softly.assertThat(Position(5, -1) moveToSameColumnOrRow  Position(0,0)).`as`("column row 3").isEqualTo(Position(5,0 ))
            softly.assertThat(Position(-1, -5) moveToSameColumnOrRow  Position(0,0)).`as`("column row 4").isEqualTo(Position(0,-5 ))
            softly.assertThat(Position(-1, 5) moveToSameColumnOrRow  Position(0,0)).`as`("column row 5").isEqualTo(Position(0,5 ))
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
