package be.moac.aoc2022

import kotlin.math.absoluteValue

fun main() {
    val input: List<String> = "/day15_input.txt".readLines { it }

    println("Part one: ${timed(0) { Day15.partOne(input, 2000_000L) }}")
//    println("Part two: ${timed { Day15 partTwo input }}")
}

object Day15 {

    fun partOne(input: List<String>, lineNumber: Long): Long {
        val signals = input.parse()//.print { this.joinToString(separator = "\n") { "[${it.sensor.x},${it.sensor.y}] - [${it.beacon.x},${it.beacon.y}]: ${it.distance}" }}

        val minX = signals.minOf { it.sensor.x - it.distance }
        val maxX = signals.maxOf { it.sensor.x + it.distance }

       return (minX .. maxX).map { it to lineNumber }
            .filterNot { (x,y) -> signals.any { it.beacon == Beacon(x,y) }}
            .filter { (x, y) ->
                signals.any { signal -> signal.distance >= Couple(signal.sensor, Beacon(x, y)).distance }
            }.size.toLong().print()

    }
    infix fun partTwo(input: List<String>): Long = TODO()



    private data class Couple(val sensor: Sensor, val beacon: Beacon) {
        val distance: Long = (sensor.x - beacon.x).absoluteValue + (sensor.y - beacon.y).absoluteValue
    }
    private data class Sensor(val x: Long, val y: Long)
    private data class Beacon(val x: Long, val y: Long)


    private val regex = "Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)".toRegex()

    private fun List<String>.parse() = this.map { it.parse() }
    private fun String.parse(): Couple {
        val (x1,y1,x2,y2) = regex.find(this)!!.destructured
        return Couple(Sensor(x1.toLong(), y1.toLong()), Beacon(x2.toLong(), y2.toLong()))
    }
}
