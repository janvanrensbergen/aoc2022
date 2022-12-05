package be.moac.oac2022

fun main() {
    val input: List<String> = "/day03_input.txt".readLines { it }

    println("Part one: ${timed { Day03 partOne input }}")
    println("Part two: ${timed { Day03 partTwo input }}")
}

object Day03 {

    infix fun partOne(input: List<String>): Int =
        input.flatMap {
            val (left, right) = it.chunked(it.length / 2).map { group -> group.toSet() }
            left.intersect(right.toSet())
        }.sumOf { it.myCode }

    infix fun partTwo(input: List<String>): Int =
        input.windowed(3, 3)
            .flatMap { it.drop(1).fold(it.first().toSet()) { acc, s -> acc.intersect(s.toSet()) } }
            .sumOf { it.myCode }

}


private val Char.myCode
    get() =
        when {
            this.isUpperCase() -> this.code - 38
            else -> this.code - 96
        }
