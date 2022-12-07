package be.moac.aoc2022

fun main() {
    val input: List<String> = "/day04_input.txt".readLines { it }

    println("Part one: ${timed { Day04 partOne input }}")
    println("Part two: ${timed { Day04 partTwo input }}")
}

object Day04 {

    infix fun partOne(input: List<String>): Int =
        input.map {
            it.split(",").map { s -> s.split("-").first().toInt() .. s.split("-").last().toInt()  }
        }.filter { pairs ->
            pairs.first().all { pairs.last().contains(it) } ||
            pairs.last().all { pairs.first().contains(it) }
        }.size


    infix fun partTwo(input: List<String>): Int =
        input.map {
            it.split(",").map { s -> s.split("-").first().toInt() .. s.split("-").last().toInt()  }
        }.filter { pairs ->
            pairs.first().any { pairs.last().contains(it) } ||
            pairs.last().any { pairs.first().contains(it) }
        }.size
}

