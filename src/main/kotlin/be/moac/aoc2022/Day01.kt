package be.moac.aoc2022

fun main() {
    val input: List<String> = "/day01_input.txt".readLines { it }

    println("Part one: ${timed { Day01 partOne input }}")
    println("Part two: ${timed { Day01 partTwo input }}")
}

object Day01 {

    infix fun partOne(input: List<String>): Long =
        input.parse().maxOf { it.calories }


    infix fun partTwo(input: List<String>): Long =
        input.parse()
            .sortedByDescending { it.calories }
            .take(3)
            .sumOf { it.calories }

    private fun List<String>.parse() =
        this.fold(listOf(Elve())) { acc, calories ->
            when {
                calories.isBlank() -> acc + Elve()
                else -> acc.dropLast(1) + acc.last().add(calories.toLong())
            }
        }


    data class Elve(val calories: Long = 0L) {
        fun add(calories: Long) = Elve(this.calories + calories)
    }

}
