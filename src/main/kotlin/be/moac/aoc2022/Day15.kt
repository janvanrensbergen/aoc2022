package be.moac.aoc2022

import kotlin.math.absoluteValue

fun main() {
    val input: List<String> = "/day15_input.txt".readLines { it }

    println("Part one: ${timed(5) { Day15.partOne(input, 2000_000L) }}")
    println("Part two: ${timed(5) { Day15.partTwo(input, 0, 4_000_000L) }}")
}

object Day15 {

    fun partOne(input: List<String>, lineNumber: Long): Long {
        val signals = input.parse()

        val minX = signals.minOf { it.sensor.x - it.distance }
        val maxX = signals.maxOf { it.sensor.x + it.distance }

        return (minX..maxX).map { it to lineNumber }
            .filterNot { (x, y) -> signals.any { it.beacon == Beacon(x, y) } }
            .filter { (x, y) ->
                signals.any { signal -> signal.distance >= Signal(signal.sensor, Beacon(x, y)).distance }
            }.size.toLong()

    }

    fun partTwo(input: List<String>, min: Long, max: Long): Long {
        val signals = input.parse()

        val result = signals.flatMap { it.edges() }
            .asSequence()
            .filter { it.x in min..max && it.y in min..max }
            .filterNot { (x, y) ->
                signals.any { signal -> signal.distance >= distance(signal.sensor.x, signal.sensor.y, x, y) }
            }
            .first()

        return (4_000_000L * result.x) + result.y
    }


    data class Signal(val sensor: Sensor, val beacon: Beacon) {
        val distance: Long = distance(sensor.x, sensor.y, beacon.x, beacon.y)

        fun edges(): List<Point> {
            val leftCorner = Point(sensor.x - distance - 1, sensor.y)
            val rightCorner = Point(sensor.x + distance + 1, sensor.y)
            return (0..distance + 1).flatMap { a ->
                listOf(
                    Point(leftCorner.x + a, leftCorner.y - a),
                    Point(leftCorner.x + a, leftCorner.y + a),
                    Point(rightCorner.x - a, rightCorner.y - a),
                    Point(rightCorner.x - a, rightCorner.y + a),
                )
            }
        }
    }

    private fun distance(x1: Long, y1: Long, x2: Long, y2: Long) =
        (x1 - x2).absoluteValue + (y1 - y2).absoluteValue


    data class Sensor(val x: Long, val y: Long)
    data class Beacon(val x: Long, val y: Long)
    data class Point(val x: Long, val y: Long)


    val regex = "Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)".toRegex()

    fun List<String>.parse() = this.map { it.parse() }
    fun String.parse(): Signal {
        val (x1, y1, x2, y2) = regex.find(this)!!.destructured
        return Signal(Sensor(x1.toLong(), y1.toLong()), Beacon(x2.toLong(), y2.toLong()))
    }
}
