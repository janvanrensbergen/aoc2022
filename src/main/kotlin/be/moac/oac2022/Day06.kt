package be.moac.oac2022

fun main() {
    val input: String = "/day06_input.txt".readLines { it }.first()

    println("Part one: ${timed { Day06 partOne input }}")
    println("Part two: ${timed { Day06 partTwo input }}")
}

object Day06 {
    infix fun partOne(input: String): Int = input.startOfPacketMarker
    infix fun partTwo(input: String): Int = input.startOfMessageMarker
}

private val String.startOfPacketMarker get() = this.markerIndex(4)
private val String.startOfMessageMarker get() = this.markerIndex(14)

private fun String.markerIndex(size: Int) =
    this.windowed(size).indexOfFirst { it.toSet().size == size }.plus(size)

