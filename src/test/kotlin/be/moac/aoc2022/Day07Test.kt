package be.moac.aoc2022

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class Day07Test {

    val input = """
        ${'$'} cd /
        ${'$'} ls
        dir a
        14848514 b.txt
        8504156 c.dat
        dir d
        ${'$'} cd a
        ${'$'} ls
        dir e
        29116 f
        2557 g
        62596 h.lst
        ${'$'} cd e
        ${'$'} ls
        584 i
        ${'$'} cd ..
        ${'$'} cd ..
        ${'$'} cd d
        ${'$'} ls
        4060174 j
        8033020 d.log
        5626152 d.ext
        7214296 k
    """.trimIndent().lines()

    @Test
    fun `calculate the sum of directories with total size is at most 100_000`() {
        assertThat(Day07 partOne input).isEqualTo(95_437)
    }

    @Test
    fun `find directory to delete`() {
        assertThat(Day07 partTwo input).isEqualTo(24_933_642)
    }
}
