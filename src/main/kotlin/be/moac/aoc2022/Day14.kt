package be.moac.aoc2022

fun main() {
    val input: List<String> = "/day14_input.txt".readLines { it }

    println("Part one: ${timed { Day14 partOne input }}")
    println("Part two: ${timed { Day14 partTwo input }}")
}

object Day14 {

    infix fun partOne(input: List<String>): Long = TODO()
    infix fun partTwo(input: List<String>): Long = TODO()

}
