package be.moac.oac2022

fun main() {
    val input: List<Long> = "/day01_input.txt".readLines { it.toLong() }

    println("Part one: ${timed { Day01 partOne input }}")
    println("Part two: ${timed { Day01 partTwo input }}")
}

object Day01 {

    infix fun partOne(input: List<Long>): Long = 1L
    infix fun partTwo(input: List<Long>): Long = 1L

}
