package be.moac.oac2022

import org.assertj.core.api.SoftAssertions.assertSoftly
import org.junit.jupiter.api.Test

class Day06Test {

    @Test
    fun `find start-of-packet marker`() {
        assertSoftly { softly ->
            softly.assertThat(Day06 partOne "mjqjpqmgbljsphdztnvjfqwrcgsmlb").isEqualTo(7)
            softly.assertThat(Day06 partOne "bvwbjplbgvbhsrlpgdmjqwftvncz").isEqualTo(5)
            softly.assertThat(Day06 partOne "nppdvjthqldpwncqszvftbrmjlhg").isEqualTo(6)
            softly.assertThat(Day06 partOne "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg").isEqualTo(10)
            softly.assertThat(Day06 partOne "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw").isEqualTo(11)
        }
    }

    @Test
    fun `find start-of-message marker`() {
        assertSoftly { softly ->
            softly.assertThat(Day06 partTwo "mjqjpqmgbljsphdztnvjfqwrcgsmlb").isEqualTo(19)
            softly.assertThat(Day06 partTwo "bvwbjplbgvbhsrlpgdmjqwftvncz").isEqualTo(23)
            softly.assertThat(Day06 partTwo "nppdvjthqldpwncqszvftbrmjlhg").isEqualTo(23)
            softly.assertThat(Day06 partTwo "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg").isEqualTo(29)
            softly.assertThat(Day06 partTwo "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw").isEqualTo(26)
        }
    }

}
