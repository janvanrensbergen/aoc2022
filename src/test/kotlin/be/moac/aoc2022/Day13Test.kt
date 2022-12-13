package be.moac.aoc2022

import be.moac.aoc2022.PacketData.IntData
import be.moac.aoc2022.PacketData.Nested
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
    """.trimIndent()

    fun print(data: PacketData<*>): String {
        return when (data) {
            is IntData -> data.data.joinToString(prefix = "", separator = ", ", postfix = "") { "$it" }
            is Nested -> data.data.joinToString(prefix = "[", separator = ", ", postfix = "]") { "${print(it)}" }
        }
    }

    @Test
    fun `that string can be parsed`() {
        assertSoftly { softly ->

//            softly.assertThat("[1,1,3,1,1]".parseData()).isEqualTo(Nested(listOf(IntData(listOf(1, 1, 3, 1, 1)))))
//            softly.assertThat("[1]".parseData()).isEqualTo(Nested(listOf(IntData(listOf(1)))))
//            softly.assertThat("[[1]]".parseData()).isEqualTo(Nested(listOf(Nested(listOf(IntData(listOf(1)))))))
//            softly.assertThat("[]".parseData()).isEqualTo(IntData(listOf()))

            val parseData = "[[1,2],[3,4]]".parseData()
            println(print(parseData))
            softly.assertThat(parseData).isEqualTo(
                Nested(
                    listOf(
                        Nested(listOf(IntData(listOf(1,2)))),
                        Nested(listOf(IntData(listOf(3,4))))
                    )
                )
            )

        }

    }
}
