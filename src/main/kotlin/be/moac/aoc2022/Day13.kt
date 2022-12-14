package be.moac.aoc2022

import java.util.*
import kotlin.collections.ArrayDeque

fun main() {
    val input: List<String> = "/day13_input.txt".readLines { it }

    println("Part one: ${timed { Day13 partOne input }}")
    println("Part two: ${timed { Day13 partTwo input }}")
}

object Day13 {

    infix fun partOne(input: List<String>): Long {
       return input.windowed(3, 3, true)
           .asSequence()
            .mapIndexed { index, it -> index + 1 to it[0].parseData().compareRightOrder(it[1].parseData()) }
            .onEach { println("${it.first}: ${it.second}") }
            .filter { (_, result) -> result }
            .sumOf { it.first }
            .toLong()
    }

    infix fun partTwo(input: List<String>): Long = TODO()

}


fun String.parseData(): PacketData<*> {
    val queue = this.fold(ArrayDeque<PacketData<*>>() to "") { (queue, current), char ->
        when (char) {
            '[' -> {
                queue.addLast(PacketData.Nested(emptyList()))
                queue to ""
            }

            ',' -> {
                if (current.isNotBlank()) {
                    when (val last = queue.removeLast()) {
                        is PacketData.IntData -> TODO()
                        is PacketData.Nested -> queue.addLast(last.copy(data = last.data + PacketData.IntData(current.toInt())))
                    }
                }
                queue to ""
            }

            ']' -> {
                if (current.isNotBlank()) {
                    when (val last = queue.removeLast()) {
                        is PacketData.IntData -> TODO()
                        is PacketData.Nested -> queue.addLast(last.copy(data = last.data + PacketData.IntData(current.toInt())))
                    }
                }

                val last = queue.removeLast()
                when {
                    queue.isEmpty() -> queue.addLast(last)
                    else -> {
                        val l = queue.removeLast()
                        when (l) {
                            is PacketData.IntData -> TODO()
                            is PacketData.Nested -> queue.addLast(l.copy(data = l.data + last))
                        }
                    }
                }
                queue to ""
            }

            else -> queue to "$current$char"
        }
    }
    return queue.first.first() //.print()
}

fun PacketData<*>.print(): PacketData<*> {
    fun join(data: PacketData<*>): String {
        return when (data) {
            is PacketData.IntData -> data.data.joinToString(prefix = "", separator = ",", postfix = "") { "$it" }
            is PacketData.Nested -> data.data.joinToString(prefix = "[", separator = ",", postfix = "]") { join(it) }
        }
    }

    println(join(this))
    return this
}


sealed interface PacketData<T> {
    fun compareRightOrder(other: PacketData<*>): Boolean {
        fun compare(left: PacketData<*>, right: PacketData<*> ): Boolean? {
            val fold = left.data.foldIndexed(null as Boolean?) { index, result, next ->
                val otherNext = right.data.getOrNull(index)
                when {
                    result != null -> result
                    otherNext == null -> false
                    next is IntData && otherNext is IntData && next == otherNext -> null
                    next is IntData && otherNext is IntData -> next < otherNext
                    next is Nested && otherNext is Nested -> compare(next, otherNext)
                    next is IntData && otherNext is Nested -> compare(Nested(listOf(next)), otherNext)
                    next is Nested && otherNext is IntData -> compare(next, Nested(listOf(otherNext)))
                    else -> null
                }
            }
            return fold ?: if (left.data.size == right.data.size) null else left.data.size < right.data.size
        }
        return compare(this, other) ?: (this.data.size < other.data.size)
    }

    val data: List<T>

    data class Nested(override val data: List<PacketData<*>>) : PacketData<PacketData<*>>
    data class IntData(val value: Int) : PacketData<Int> {
        override val data: List<Int>
            get() = listOf(value)

        operator fun compareTo(other: IntData): Int {
            return value.compareTo(other.value)
        }
    }
}


