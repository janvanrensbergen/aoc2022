package be.moac.oac2022

import be.moac.oac2022.Result.*
import kotlin.Int
import kotlin.Pair
import kotlin.String
import kotlin.to
import kotlin.with

fun main() {
    val input: List<Pair<String, String>> = "/day02_input.txt".readLines { with(it.split(" ")) { first() to last() } }

    println("Part one: ${timed { Day02 partOne input }}")
    println("Part two: ${timed { Day02 partTwo input }}")
}

object Day02 {

    infix fun partOne(input: List<Pair<String, String>>): Int =
        input.map { Shape.of(it.first) to Shape.of(it.second) }
            .sumOf { (elve, me) -> me.play(elve) }


    infix fun partTwo(input: List<Pair<String, String>>): Int =
        input.asSequence()
            .map { Shape.of(it.first) to Result.of(it.second) }
            .map { it.first to it.first.select(it.second) }
            .sumOf { (elve, me) -> me.play(elve) }

}


private enum class Shape(
    private val value: List<String>,
    private val score: Int
) {
    Rock(listOf("A", "X"), 1),
    Paper(listOf("B", "Y"), 2),
    Scissor(listOf("C", "Z"), 3);

    fun play(other: Shape): Int =
        when (other) {
            this -> this.score + Draw.score
            else -> states[this to other]!!.score + this.score
        }

    fun select(result: Result) =
        when (result) {
            Draw -> this
            else -> states.filter { it.value == result }
                .filter { it.key.second == this }
                .map { it.key.first }
                .first()
        }

    companion object {
        fun of(value: String) = Shape.values().first { it.value.contains(value) }
    }
}

private enum class Result(private val value: String, val score: Int) {
    Loss("X", 0),
    Draw("Y", 3),
    Win("Z", 6);

    companion object {
        fun of(value: String) = Result.values().first { it.value == value }
    }
}

private val states
    get() =
        mapOf(
            (Shape.Rock to Shape.Scissor) to Win,
            (Shape.Rock to Shape.Paper) to Loss,
            (Shape.Paper to Shape.Rock) to Win,
            (Shape.Paper to Shape.Scissor) to Loss,
            (Shape.Scissor to Shape.Paper) to Win,
            (Shape.Scissor to Shape.Rock) to Loss
        )
