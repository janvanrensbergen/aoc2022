package be.moac.aoc2022

import be.moac.aoc2022.PacketData.IntData
import be.moac.aoc2022.PacketData.Nested
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.SoftAssertions.assertSoftly
import org.junit.jupiter.api.Test

class Day13Test {
    private val input = """
        [1,1,3,1,1]
        [1,1,5,1,1]

        [[1],[2,3,4]]
        [[1],4]

        [9]
        [[8,7,6]]

        [[4,4],4,4]
        [[4,4],4,4,4]

        [7,7,7,7]
        [7,7,7]

        []
        [3]

        [[[]]]
        [[]]

        [1,[2,[3,[4,[5,6,7]]]],8,9]
        [1,[2,[3,[4,[5,6,0]]]],8,9]
    """.trimIndent().lines()

    @Test
    fun `part one`() {
        assertThat(Day13 partOne input).isEqualTo(13)
    }
    @Test
    fun `part two`() {
        assertThat(Day13 partTwo input).isEqualTo(140)
    }

    @Test
    fun `compare data`() {
        assertSoftly { softly ->
            softly.assertThat("[9]".parseData() < ("[[8,10,6]]".parseData())).`as`("7_a").isFalse
            softly.assertThat("[1,1,3,1,1]".parseData() < ("[1,1,5,1,1]".parseData())).`as`("1").isTrue
            softly.assertThat("[1,1,6,1,1]".parseData() < ("[1,1,5,1,1]".parseData())).`as`("2").isFalse
            softly.assertThat("[1,1,6,1,1]".parseData() < ("[1,1,5,2,2]".parseData())).`as`("2_a").isFalse
            softly.assertThat("[[1]]".parseData() < ("[[2]]".parseData())).`as`("3").isTrue
            softly.assertThat("[[2]]".parseData() < ("[[1]]".parseData())).`as`("4").isFalse
            softly.assertThat("[1]".parseData() < ("[[2]]".parseData())).`as`("5").isTrue
            softly.assertThat("[2]".parseData() < ("[[1]]".parseData())).`as`("6").isFalse
            softly.assertThat("[9]".parseData() < ("[[8,7,6]]".parseData())).`as`("7").isFalse
            softly.assertThat("[[4,4],4,4]".parseData() < ("[[4,4],4,4,4]".parseData())).`as`("8").isTrue
            softly.assertThat("[7,7,7,7]".parseData() < ("[7,7,7]".parseData())).`as`("9").isFalse
            softly.assertThat("[]".parseData() < ("[3]".parseData())).`as`("10").isTrue
            softly.assertThat("[[3]]".parseData() < ("[]".parseData())).`as`("10_a").isFalse
            softly.assertThat("[[[]]]".parseData() < ("[[]]".parseData())).`as`("10_b").isFalse
            softly.assertThat("[[]]".parseData() < ("[[[]]]".parseData())).`as`("10_c").isTrue
            softly.assertThat("[1,[2,[3,[4,[5,6,7]]]],8,9]".parseData() < ("[1,[2,[3,[4,[5,6,0]]]],8,9]".parseData())).`as`("10_d").isFalse
            softly.assertThat("[[4,4],4]".parseData() < ("[[4,4],5]".parseData())).`as`("11").isTrue
            softly.assertThat("[[4,4],4]".parseData() < ("[[4,4],3]".parseData())).`as`("12").isFalse
            softly.assertThat("[[4,4,4],4]".parseData() < ("[[4,4],4]".parseData())).`as`("13").isFalse
            softly.assertThat("[[4,4],4]".parseData() < ("[[4,4,4],4]".parseData())).`as`("14").isTrue
            softly.assertThat("[[1],[2,3,4]]".parseData() < ("[[1],4]".parseData())).`as`("15").isTrue

            softly.assertThat("[[[1]]]".parseData() < ("[4]".parseData())).`as`("16").isTrue
            softly.assertThat("[[[1]]]".parseData() < ("[[4]]".parseData())).`as`("17").isTrue
            softly.assertThat("[[[1]]]".parseData() < ("[[[4]]]".parseData())).`as`("18").isTrue

        }
    }

    @Test
    fun sort() {
        listOf("[1,1,6,1,1]".parseData(), "[1,1,5,1,1]".parseData())
            .sorted()
            .onEach { it.print() }
    }

    @Test
    fun `that string can be parsed`() {
        assertSoftly { softly ->
            softly.assertThat("[1,1,3,1,1]".parseData()).isEqualTo(
                Nested(
                    listOf(
                        IntData(1),
                        IntData(1),
                        IntData(3),
                        IntData(1),
                        IntData(1),
                    )
                )
            )
            softly.assertThat("[1]".parseData()).isEqualTo(Nested(listOf(IntData(1))))
            softly.assertThat("[[1]]".parseData()).isEqualTo(Nested(listOf(Nested(listOf(IntData(1))))))
            softly.assertThat("[]".parseData()).isEqualTo(Nested(listOf()))

            softly.assertThat("[[1,2],[3,4]]".parseData()).isEqualTo(
                Nested(
                    listOf(
                        Nested(listOf(IntData(1), IntData(2))),
                        Nested(listOf(IntData(3), IntData(4)))
                    )
                )
            )

            softly.assertThat("[[1],4]".parseData()).isEqualTo(
                Nested(
                    listOf(
                        Nested(listOf(IntData(1))),
                        IntData(4)
                    )
                )
            )
            softly.assertThat("[1,[2,[3,[4,[5,6,7]]]],8,9,100]".parseData()).isEqualTo(
                Nested(
                    listOf(
                        IntData(1),
                        Nested(
                            listOf(
                                IntData(2),
                                Nested(
                                    listOf(
                                        IntData(3),
                                        Nested(
                                            listOf(
                                                IntData(4),
                                                Nested(
                                                    listOf(
                                                        IntData(5),
                                                        IntData(6),
                                                        IntData(7),
                                                    )
                                                )
                                            )
                                        )
                                    )
                                )
                            )
                        ),
                        IntData(8),
                        IntData(9),
                        IntData(100),
                    )
                )
            )
        }

    }


}
