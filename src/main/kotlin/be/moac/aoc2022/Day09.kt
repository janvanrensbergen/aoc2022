package be.moac.aoc2022

import kotlin.math.abs

fun main() {
    val input: List<String> = "/day09_input.txt".readLines { it }

    println("Part one: ${timed(10) { Day09 partOne input }}")
    println("Part two: ${timed(10) { Day09 partTwo input }}")
}

object Day09 {

    infix fun partOne(input: List<String>): Int = input.moves
        .fold(Grid(Position(0, 0), listOf(Position(0, 0)))) { acc, move -> acc.move(move) }.steps

    infix fun partTwo(input: List<String>): Int = input.moves
        .fold(Grid(Position(0, 0), (1..9).map { Position(0, 0) })) { acc, move -> acc.move(move) }.steps

}

data class Grid(val head: Position, val tail: List<Position>, val tailSteps: List<Position> = emptyList()) {

    val steps get() =
        tailSteps.toSet().size

    fun move(move: Move): Grid {
        val headPosition = head.move(move)
        val tailPosition = tail.drop(1).fold(listOf((tail.first() moveCloserTo headPosition))) { acc, position ->
            acc + (position moveCloserTo acc.last().first)
        }
        return this.copy(head = headPosition, tail = tailPosition.map { it.first }, tailSteps = this.tailSteps + tailPosition.last().second)
    }

    fun printTailSteps(): Grid {
        println("    TAIL 9     ")
        (10 downTo 0).forEach { y ->
            val line = (0..10).joinToString("") { x ->
                when {
                    Position(0, 0) == Position(x, y) -> "s"
                    tailSteps.contains(Position(x, y)) -> "#"
                    else -> "."
                }
            }
            println(line)
        }

        return this
    }

    fun print(): Grid {
        (10 downTo 0).forEach { y ->
            val line = (0..10).joinToString("") { x ->
                when {
                    head == Position(x, y) -> "H"
                    tail.contains(Position(x, y)) -> "${tail.indexOf(Position(x, y)) + 1}"
                    else -> "."
                }
            }
            println(line)
        }
        return this
    }
}

data class Position(val x: Int, val y: Int) {

    infix fun isTouching(position: Position) =
        position.x in (this.x - 1..this.x + 1) && position.y in (this.y - 1..this.y + 1)

    infix fun moveCloserTo(position: Position): Pair<Position, List<Position>> {
        fun moveIt(following: Position, main: Position, steps: List<Position>): Pair<Position, List<Position>> {
            return when {
                following isTouching main -> following to steps
                else -> {
                    val x = when {
                        following isLeftOf main && following isAboveOf main -> following.move(Move.Right(1)).move(Move.Down(1))
                        following isRightOf main && following isAboveOf main -> following.move(Move.Left(1)).move(Move.Down(1))
                        following isLeftOf main && following isBelowOf main -> following.move(Move.Right(1)).move(Move.Up(1))
                        following isRightOf main && following isBelowOf main -> following.move(Move.Left(1)).move(Move.Up(1))
                        following isLeftOf main -> following.move(Move.Right(1))
                        following isRightOf main -> following.move(Move.Left(1))
                        following isAboveOf main -> following.move(Move.Down(1))
                        following isBelowOf main -> following.move(Move.Up(1))
                        else -> following
                    }
                    moveIt(x, main, steps + x)
                }
            }
        }

        return moveIt(this, position, listOf(this))

    }

    fun move(move: Move) =
        when (move) {
            is Move.Down -> this.copy(y = this.y - move.steps)
            is Move.Left -> this.copy(x = this.x - move.steps)
            is Move.Right -> this.copy(x = this.x + move.steps)
            is Move.Up -> this.copy(y = this.y + move.steps)
        }

    infix fun isLeftOf(position: Position) = this.x < position.x
    infix fun isRightOf(position: Position) = this.x > position.x
    infix fun isAboveOf(position: Position) = this.y > position.y
    infix fun isBelowOf(position: Position) = this.y < position.y

}

private val List<String>.moves: List<Move>
    get() {
        return this.flatMap {
            val (move, steps) = lineRegex.find(it)!!.destructured
            (1..steps.toInt()).map { Move.of(move, 1) }
        }
    }

sealed class Move(open val steps: Int) {
    data class Right(override val steps: Int) : Move(steps)
    data class Up(override val steps: Int) : Move(steps)
    data class Left(override val steps: Int) : Move(steps)
    data class Down(override val steps: Int) : Move(steps)

    companion object {
        fun of(move: String, steps: Int): Move {

            return when (move) {
                "R" -> Right(steps)
                "U" -> Up(steps)
                "L" -> Left(steps)
                "D" -> Down(steps)
                else -> throw RuntimeException()
            }
        }
    }
}

private val lineRegex = "(.+) (\\d+)".toRegex()
