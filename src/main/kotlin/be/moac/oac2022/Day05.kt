package be.moac.oac2022

fun main() {
    val input: List<String> = "/day05_input.txt".readLines { it }

    println("Part one: ${timed { Day05 partOne input }}")
    println("Part two: ${timed { Day05 partTwo input }}")
}

object Day05 {

    infix fun partOne(input: List<String>): String {
        val index = input.indexOfFirst { it.isBlank() }
        val stacks = parseStacks(input, index)
        val commands = parseCommands(input, index)

        commands.forEach { command ->
            repeat(command.numberOfMoves) {
                stacks[command.to]!!.addLast(stacks[command.from]!!.removeLast())
            }
        }


        return stacks.values.joinToString(separator = "") { it.last() }
    }

    infix fun partTwo(input: List<String>): String {
        val index = input.indexOfFirst { it.isBlank() }
        val stacks = parseStacks(input, index)
        val commands = parseCommands(input, index)

        commands.forEach { command ->
            val temp = (0 until command.numberOfMoves).map { stacks[command.from]!!.removeLast() }.reversed()
            stacks[command.to]!!.addAll(temp)
        }

        return stacks.values.joinToString(separator = "") { it.last() }
    }
}

val regex = """move (\d+) from (\d+) to (\d+)""".toRegex()

private fun parseCommands(input: List<String>, index: Int) = input.subList(index + 1, input.size)
    .map {
        val (value, from, to) = regex.find(it)!!.destructured
        Command(value.toInt(), from.toInt(), to.toInt())
    }

private fun parseStacks(
    input: List<String>,
    index: Int
) = input.subList(0, index)
    .reversed()
    .foldIndexed(mapOf<Int, ArrayDeque<String>>()) { index, acc, s ->
        when (index) {
            0 -> s.split(" ").filter { it.isNotBlank() }.associate { it.trim().toInt() to ArrayDeque() }
            else -> {
                s.windowed(4, 4, true).forEachIndexed { index, crate ->
                    if (crate.trim().isNotBlank()) acc[index + 1]!!.addLast(crate.trim().removePrefix("[").removeSuffix("]"))
                }
                acc
            }
        }
    }

data class Command(val numberOfMoves: Int, val from: Int, val to: Int)
