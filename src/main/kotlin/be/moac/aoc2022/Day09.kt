package be.moac.aoc2022

import kotlin.math.abs

fun main() {
    val input: List<String> = "/day09_input.txt".readLines { it }

    println("Part one: ${timed(1) { Day09 partOne input }}")
    println("Part two: ${timed { Day09 partTwo input }}")
}

object Day09 {

    infix fun partOne(input: List<String>): Int {
        val grid = input.moves
            .fold(Grid(Position(0, 0), Position(0, 0))) { acc, move ->
                acc.move(move)
            }

        return grid.tailSteps.size
    }
    infix fun partTwo(input: List<String>): Int = TODO()

}

data class Grid(val head: Position, val tail: Position, val tailSteps: Set<Position> = emptySet()) {
    fun move(move: Move): Grid {
        val headPosition = head.move(move)
        val tailPosition = tail moveCloserTo headPosition
        return this.copy(head = headPosition, tail= tailPosition.first, tailSteps = this.tailSteps + tailPosition.second)
    }
}

data class Position(val x:Int, val y:Int) {

    infix fun isTouching(position: Position) =
        listOf(
            this.copy(x = this.x - 1, y = this.y + 1), this.copy(x = this.x, y = this.y + 1), this.copy(x = this.x + 1, y = this.y + 1),
            this.copy(x = this.x - 1, y = this.y), this.copy(x = this.x, y = this.y), this.copy(x = this.x + 1, y = this.y),
            this.copy(x = this.x - 1, y = this.y - 1), this.copy(x = this.x, y = this.y - 1), this.copy(x = this.x + 1, y = this.y - 1),
        ).contains(position)

    infix fun moveCloserTo(position: Position): Pair<Position, Set<Position>> {
        fun moveIt(following: Position, main: Position, steps: Set<Position>) : Pair<Position, Set<Position>> {
            return when {
                following isTouching main -> following to steps
                else -> {
                    val x = when {
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

        return moveIt(this moveToSameColumnOrRow position, position, setOf(this))
    }

    fun move(move: Move) =
        when(move){
            is Move.Down -> this.copy(y = this.y - move.steps)
            is Move.Left -> this.copy(x = this.x - move.steps)
            is Move.Right -> this.copy(x = this.x + move.steps)
            is Move.Up -> this.copy(y = this.y + move.steps)
        }

    infix fun isLeftOf(position: Position) = this.x < position.x
    infix fun isRightOf(position: Position) = this.x > position.x
    infix fun isAboveOf(position: Position) = this.y > position.y
    infix fun isBelowOf(position: Position) = this.y < position.y
    infix fun moveToSameColumnOrRow(position: Position) =
        when {
            this isTouching position -> this
            abs(position.x - this.x) < abs(position.y - this.y) -> this.copy(x = position.x)
            else -> this.copy(y = position.y)
        }
}

private val List<String>.moves: List<Move> get() =
    this.map { Move.of(it)}

sealed class Move(open val steps: Int) {
    data class Right(override val steps: Int): Move(steps)
    data class Up(override val steps: Int): Move(steps)
    data class Left(override val steps: Int): Move(steps)
    data class Down(override val steps: Int): Move(steps)

    companion object {
        fun of(value: String):Move {
            val(move, steps) = lineRegex.find(value.trim())!!.destructured
            return when(move) {
                "R" -> Right(steps.toInt())
                "U" -> Up(steps.toInt())
                "L" -> Left(steps.toInt())
                "D" -> Down(steps.toInt())
                else -> throw RuntimeException()
            }
        }
    }
}

private val lineRegex = "(.+) (\\d+)".toRegex()
