package be.moac.aoc2022

import be.moac.aoc2022.Day14.Result.Sand
import kotlin.math.absoluteValue

fun main() {
    val input: List<String> = "/day14_input.txt".readLines { it }

    println("Part one: ${timed(0) { Day14 partOne input }}")
    println("Part two: ${timed(0) { Day14 partTwo input }}")
}

object Day14 {

    private val movements = listOf(Coordinate(0, -1), Coordinate(-1, -1), Coordinate(1, -1))

    infix fun partOne(input: List<String>): Int {
        val rocks = input.parseRocks()
        val lowest = rocks.minBy { it.y }.y

        tailrec fun drop(current: Coordinate = Coordinate(500, 0),
                 sand: List<Coordinate> = emptyList()): Result {
            val next = movements.map { it + current }
                .filterNot { (rocks + sand).contains(it) }
            return when {
                next.isEmpty() -> Sand( sand + current)
                next.first().y < lowest  -> Result.Done(sand.size)
                else -> drop(next.first(), sand)
            }
        }

        tailrec fun untilTheEndlessVoidComes(result: Result): Result {
            return when(result) {
                is Result.Done -> result
                is Sand -> untilTheEndlessVoidComes(drop(sand = result.value))
            }
        }

        return (untilTheEndlessVoidComes(Sand()) as Result.Done).result
    }

    infix fun partTwo(input: List<String>): Int {
        val rocks = input.parseRocks()
        val floor = rocks.minBy { it.y }.y  -2

        tailrec fun drop(current: Coordinate = Coordinate(500, 0),  sand: List<Coordinate> = emptyList()): Result {
            val next = movements.map { it + current }
                .filterNot { it.y == floor }
                .filterNot { rocks.contains(it) }
                .filterNot { sand.contains(it) }

            return when {
                next.isEmpty() && current == Coordinate(500, 0) -> Result.Done(sand.size + 1)
                next.isEmpty() -> Sand( sand + current)
                else -> drop(next.first(), sand)
            }
        }

        tailrec fun untilItsFull(result: Result): Result {
            return when(result) {
                is Result.Done -> result
                is Sand -> untilItsFull(drop(sand = result.value))
            }
        }

        return (untilItsFull(Sand()) as Result.Done).result
    }


    private sealed interface Result {

        data class Sand(val value: List<Coordinate> = emptyList()): Result
        data class Done(val result: Int): Result
    }

    private fun print(rocks: List<Coordinate>, sand: List<Coordinate>): String {
        return (0..9).joinToString(separator = "\n") { y ->
            (494..503).joinToString(separator = "") { x ->
                when {
                    rocks.contains(Coordinate(x, -y)) -> "#"
                    sand.contains(Coordinate(x, -y)) -> "o"
                    else -> "."
                }
            }
        }
    }
    private fun print(rocks: List<Coordinate>, sand: List<Coordinate>, floor: Int): String {
        return (0..(9 + floor.absoluteValue)).joinToString(separator = "\n") { y ->
            (488..512).joinToString(separator = "") { x ->
                when {
                    rocks.contains(Coordinate(x, -y)) -> "#"
                    sand.contains(Coordinate(x, -y)) -> "o"
                    -y == floor -> "#"
                    else -> "."
                }
            }
        }
    }

    private fun List<String>.parseRocks() =
        this.flatMap { line ->
            line.split(" -> ").zipWithNext()
                .flatMap {
                    val (firstX, firstY) = it.first.split(",")
                    val (secondX, secondY) = it.second.split(",")
                    (firstX.toInt() range secondX.toInt()).flatMap { x ->
                        (-firstY.toInt() range -secondY.toInt()).map { y -> Coordinate(x, y) }
                    }
                }
        }

    private data class Coordinate(val x: Int, val y: Int) {
        operator fun plus(other: Coordinate): Coordinate = Coordinate(this.x + other.x, this.y + other.y)
    }

    private infix fun Int.range(other: Int) =
        when {
            this > other -> (this downTo other)
            else -> (this..other)

        }

}
