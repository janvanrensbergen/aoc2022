package be.moac.oac2022

fun main() {
    val input: List<String> = "/day05_input.txt".readLines { it }

    println("Part one: ${timed { Day05 partOne input }}")
    println("Part two: ${timed { Day05 partTwo input }}")
}

object Day05 {

    infix fun partOne(input: List<String>): String {
        val stacks = input.stacks
        val commands = input.commands

        commands.forEach { command ->
            repeat(command.numberOfMoves) {
                stacks[command.to]!!.addLast(stacks[command.from]!!.removeLast())
            }
        }


        return stacks.values.joinToString(separator = "") { it.last() }
    }

    infix fun partTwo(input: List<String>): String {
        val stacks = input.stacks
        val commands = input.commands


        commands.forEach { command ->
            val temp = (0 until command.numberOfMoves).map { stacks[command.from]!!.removeLast() }.reversed()
            stacks[command.to]!!.addAll(temp)
        }

        return stacks.values.joinToString(separator = "") { it.last() }
    }
}

private val List<String>.separatorIndex get() = this.indexOfFirst { it.isBlank() }

private val regex = """move (\d+) from (\d+) to (\d+)""".toRegex()
private val List<String>.commands
    get() = this.subList(this.separatorIndex + 1, this.size)
        .map {
            val (value, from, to) = regex.find(it)!!.destructured
            Command(value.toInt(), from.toInt(), to.toInt())
        }

private val List<String>.stacks get() = this.subList(0, this.separatorIndex).reversed().asStacks()

private fun String.initStacks(): Map<Int, ArrayDeque<String>> = this.split(" ").filter { it.isNotBlank() }.associate { it.trim().toInt() to ArrayDeque() }
private fun List<String>.asStacks() =
    this
        .drop(1)
        .fold(this.first().initStacks()) { acc, s ->
            acc.apply {
                s.windowed(4, 4, true)
                    .map { crate -> crate.trim().removePrefix("[").removeSuffix("]") }
                    .forEachIndexed { index, crate -> if (crate.trim().isNotBlank()) this[index + 1]!!.addLast(crate) }
            }
        }

data class Command(val numberOfMoves: Int, val from: Int, val to: Int)
